package com.nat20.ticketguru.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.dto.BasketDTO;
import com.nat20.ticketguru.dto.SaleDTO;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.UserRepository;
import com.nat20.ticketguru.service.SaleService;
import com.nat20.ticketguru.service.TicketSaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
@Validated
public class SaleRestController {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketSaleService ticketSaleService;
    private final SaleService saleService;

    public SaleRestController(SaleRepository saleRepository,
            UserRepository userRepository, TicketRepository ticketRepository,
            TicketSaleService ticketSaleService,
            SaleService saleService) {

        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.ticketSaleService = ticketSaleService;
        this.saleService = saleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_SALES')")
    public ResponseEntity<List<SaleDTO>> getAllSales() {

        List<Sale> sales = saleRepository.findAllActive();

        return ResponseEntity.ok(sales.stream()
                .map(Sale::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_SALES')")
    public ResponseEntity<SaleDTO> getSale(@PathVariable Long id) {

        Sale sale = saleRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        return ResponseEntity.ok(sale.toDTO());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SALES')")
    public ResponseEntity<SaleDTO> createSale(@Valid @RequestBody SaleDTO saleDTO) {

        User user = userRepository.findByIdActive(saleDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Sale newSale = new Sale();
        newSale.setUser(user);

        List<Ticket> validTickets = saleDTO.ticketIds().stream()
                .map(ticketId -> {
                    Ticket ticket = ticketRepository.findByIdActive(ticketId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket"));
                    ticket.setSale(newSale);
                    return ticket;
                })
                .toList();

        newSale.setTickets(validTickets);

        Sale addedSale = saleRepository.save(newSale);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedSale.toDTO());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_SALES')")
    public ResponseEntity<SaleDTO> editSale(@Valid @RequestBody SaleDTO editedSaleDTO, @PathVariable Long id) {

        Sale sale = saleRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        User existingUser = userRepository.findByIdActive(editedSaleDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));

        List<Ticket> validTickets = editedSaleDTO.ticketIds().stream()
                .map(ticketId -> {
                    Ticket ticket = ticketRepository.findByIdActive(ticketId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket"));
                    ticket.setSale(sale);
                    return ticket;
                })
                .toList();

        sale.setUser(existingUser);
        sale.setTickets(validTickets);

        Sale editedSale = saleRepository.save(sale);

        return ResponseEntity.ok(editedSale.toDTO());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SALES')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<SaleDTO> deleteSale(@PathVariable Long id) {

        Sale sale = saleRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        sale.delete();

        saleRepository.save(sale);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasAuthority('CONFIRM_SALES')")
    public ResponseEntity<?> confirmSaleFromBasket(@Valid @RequestBody BasketDTO basketDTO, @AuthenticationPrincipal User user) {
        try {
            SaleDTO sale = ticketSaleService.processSale(basketDTO, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('VIEW_SALES')")
    public ResponseEntity<List<SaleDTO>> searchSales(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) Long userId) {

        LocalDateTime startDateTime = parseToDateTime(start);
        LocalDateTime endDateTime = parseToDateTime(end);

        if (startDateTime == null && endDateTime == null && userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one search parameter must be provided.");
        }
        try {
            List<Sale> sales = saleService.searchSales(startDateTime, endDateTime, userId);
            return ResponseEntity.ok(sales.stream().map(Sale::toDTO).toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private LocalDateTime parseToDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            // parse as LocalDateTime
            return LocalDateTime.parse(dateStr);
        } catch (DateTimeParseException e) {
            try {
                // parse as LocalDate
                LocalDate localDate = LocalDate.parse(dateStr);
                return localDate.atStartOfDay(); // convert to start of day LocalDateTime object
            } catch (DateTimeParseException ex) {
                // does not parse as either
                return null;
            }
        }
    }

}

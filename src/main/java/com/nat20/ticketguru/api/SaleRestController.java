package com.nat20.ticketguru.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.nat20.ticketguru.web.TicketSaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
@Validated
public class SaleRestController {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketSaleService ticketSaleService;

    public SaleRestController(SaleRepository saleRepository, UserRepository userRepository, TicketRepository ticketRepository, TicketSaleService ticketSaleService) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.ticketSaleService = ticketSaleService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('VIEW_SALES')")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        saleRepository.findAll().forEach(sales::add);

        List<SaleDTO> saleDTOs = sales.stream()
                .filter(sale -> sale.getDeletedAt() == null)
                .map(sale -> new SaleDTO(sale.getId(),
                sale.getPaidAt(),
                sale.getUser().getId(),
                sale.getTickets().stream()
                        .map(Ticket::getId).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(saleDTOs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_SALES')")
    public ResponseEntity<SaleDTO> getSale(@PathVariable("id") Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        if (sale.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
        SaleDTO saleDTO = new SaleDTO(sale.getId(),
                sale.getPaidAt(),
                sale.getUser().getId(),
                sale.getTickets().stream()
                        .map(Ticket::getId).collect(Collectors.toList()));
        return ResponseEntity.ok(saleDTO);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('CREATE_SALES')")
    public ResponseEntity<SaleDTO> createSale(@Valid @RequestBody SaleDTO saleDTO) {
        // check User
        Optional<User> existingUser = userRepository.findById(saleDTO.userId());
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Sale newSale = new Sale();
        // check Tickets
        List<Ticket> validTickets = new ArrayList<>();
        List<Long> requestTickets = saleDTO.ticketIds();
        for (Long ticketId : requestTickets) {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            if (!ticket.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket");
            }
            // TODO maybe need to add more validation here? 
            validTickets.add(ticket.get());
            // set ticket association with sale in ticket
            ticket.get().setSale(newSale);
        }
        if (validTickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid tickets in Sale");
        }

        newSale.setUser(existingUser.get());
        newSale.setTickets(validTickets);
        if (saleDTO.paidAt() != null) {
            newSale.setPaidAt(saleDTO.paidAt());
        }
        // save the new Sale entity
        Sale savedSale = saleRepository.save(newSale);

        // package it again into saleDTO for the response body
        SaleDTO responseDTO = new SaleDTO(
                savedSale.getId(),
                savedSale.getPaidAt(),
                savedSale.getUserId(),
                savedSale.getTickets().stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_SALES')")
    public ResponseEntity<SaleDTO> editSale(@Valid @RequestBody SaleDTO editedSaleDTO, @PathVariable("id") Long id) {
        // check that sale with the Id exists
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        // check if sale has been soft-deleted
        if (sale.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
        User existingUser = userRepository.findById(editedSaleDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        // check if user has been soft-deleted
        if (existingUser.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        // check Tickets
        List<Ticket> validTickets = new ArrayList<>();
        for (Long ticketId : editedSaleDTO.ticketIds()) {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            if (!ticket.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket");
            }
            // check ticket for soft-delete status
            if (ticket.get().getDeletedAt() == null) {
                // TODO maybe need to add more validation here?
                validTickets.add(ticket.get());
                // set ticket association with sale in ticket
                ticket.get().setSale(sale);
            }

        }
        sale.setUser(existingUser);
        sale.setTickets(validTickets);
        if (editedSaleDTO.paidAt() != null) {
            sale.setPaidAt(editedSaleDTO.paidAt());
        }
        // save the edited Sale entity
        Sale savedSale = saleRepository.save(sale);
        // package it again into saleDTO for the response body
        SaleDTO responseDTO = new SaleDTO(
                savedSale.getId(),
                savedSale.getPaidAt(),
                savedSale.getUserId(),
                savedSale.getTickets().stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()));
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SALES')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable("id") Long id
    ) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        if (sale.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
        sale.setDeletedAt(LocalDateTime.now());
        saleRepository.save(sale);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasAuthority('CONFIRM_SALES')")
    public ResponseEntity<?> confirmSaleFromBasket(@Valid @RequestBody BasketDTO basketDTO, @AuthenticationPrincipal User user) {
        try {
            SaleDTO sale = ticketSaleService.processSale(basketDTO, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}

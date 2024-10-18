package com.nat20.ticketguru.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.nat20.ticketguru.dto.SaleDTO;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
@Validated
public class RestSaleController {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public RestSaleController(SaleRepository saleRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        saleRepository.findAll().forEach(sales::add);

        List<SaleDTO> saleDTOs = sales.stream()
                .map(sale -> new SaleDTO(sale.getId(),
                sale.getPaidAt(),
                sale.getUser().getId(),
                sale.getTickets().stream()
                        .map(Ticket::getId).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(saleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable("id") Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        SaleDTO saleDTO = new SaleDTO(sale.getId(),
                sale.getPaidAt(),
                sale.getUser().getId(),
                sale.getTickets().stream()
                        .map(Ticket::getId).collect(Collectors.toList()));
        return ResponseEntity.ok(saleDTO);
    }

    @PostMapping("")
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
    public ResponseEntity<SaleDTO> editSale(@Valid @RequestBody SaleDTO editedSaleDTO, @PathVariable("id") Long id) {
        // check that Id exists
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        User existingUser = userRepository.findById(editedSaleDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        // check Tickets
        List<Ticket> validTickets = new ArrayList<>();
        for (Long ticketId : editedSaleDTO.ticketIds()) {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            if (!ticket.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket");
            }
            // TODO maybe need to add more validation here? 
            validTickets.add(ticket.get());
            // set ticket association with sale in ticket
            ticket.get().setSale(sale);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable("id") Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        // remove sale association from tickets before delete (we want to preserve the tickets in the database)
        for (Ticket ticket : sale.getTickets()) {
            ticket.setSale(null);
        }
        // remove sale association from user before delete (we want to preserve the users in the database)
        User user = sale.getUser();
        if (user != null) {
            user.getSales().remove(sale);
            userRepository.save(user);
        }
        saleRepository.deleteById(id);
    }

    // Exception handler for validation errors
    // If validation fails, a MethodArgumentNotValidException is thrown,
    // which then returns the failed field(s) and the validation failure message(s)
    // as a BAD_REQUEST response
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
    // Source:
    // https://dev.to/shujaat34/exception-handling-and-validation-in-spring-boot-3of9
    // The source details a Global Exception handler, that we could implement later
    // to handle all the endpoints
}

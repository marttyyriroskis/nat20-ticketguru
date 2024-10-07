package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.repository.TicketTypeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class RestTicketController {
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final SaleRepository saleRepository;

    public RestTicketController(TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository, SaleRepository saleRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.saleRepository = saleRepository;
    }

    // Get tickets
    @GetMapping("")
    public Iterable<Ticket> getTickets() {
        return ticketRepository.findAll();
    }
    
    // Get ticket by id
    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable("id") Long ticketId) {
        // Check if Ticket exists
        Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
        return ticket;
    }
    
    // Post a new ticket
    @PostMapping("")
    public Ticket createTicket(@Valid @RequestBody Ticket ticket) {
        // Check if TicketType.id is null
        if (ticket.getTicketType() != null && ticket.getTicketType().getId() == null) {
            ticket.setTicketType(null);
        }

        // Check if TicketType already exists
        if (ticket.getTicketType() != null) {
            Optional<TicketType> existingTicketType = ticketTypeRepository.findById(ticket.getTicketType().getId());

            if (!existingTicketType.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ticket type does not exist!");
            }

            ticket.setTicketType(existingTicketType.get());
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return savedTicket;
    }
    
    // Edit ticket
    @PutMapping("/{id}")
    public Ticket editTicket(@Valid @RequestBody Ticket editedTicket, @PathVariable("id") Long ticketId, @RequestParam Long ticketTypeId, @RequestParam Long saleId) {
        // Check if Ticket exists
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (!optionalTicket.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Ticket not found");
        }
        Ticket ticket = optionalTicket.get();

        // Update fields
        ticket.setPrice(editedTicket.getPrice());

        if (editedTicket.getUsedAt() != null) {
            ticket.setUsedAt(editedTicket.getUsedAt());
        }

        /* TODO: Implement markAsUsed() method, e.g.:
        Ticket ticket = new Ticket();
        ticket.markAsUsed();
        LocalDateTime usedAtTime = ticket.getUsedAt();
        */

        // Find TicketType by ticketTypeId
        if (ticketTypeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No ticketTypeId in query parameter");
        }

        TicketType existingTicketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket type not found"));
        
        ticket.setTicketType(existingTicketType);

        // Find Sale by saleId
        if (saleId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No saleId in query parameter");
        }

        Sale existingSale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sale not found"));
        
        ticket.setSale(existingSale);

        return ticketRepository.save(ticket);
    }

    // Delete ticket
    @DeleteMapping("/{id}")
    public Iterable<Ticket> deleteTicket(@PathVariable("id") Long ticketId) {
        // Check if Ticket exists
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (!optionalTicket.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Ticket not found");
        }

        ticketRepository.deleteById(ticketId);
        return ticketRepository.findAll();
    }

}

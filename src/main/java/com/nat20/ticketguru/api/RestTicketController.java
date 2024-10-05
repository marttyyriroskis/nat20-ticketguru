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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    public RestTicketController(TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    // Get tickets
    @GetMapping("")
    public Iterable<Ticket> getTickets() {
        return ticketRepository.findAll();
    }
    
    // Get ticket by id
    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable("id") Long ticketId) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (!optionalTicket.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ticket not found");
        }
        Ticket ticket = optionalTicket.get();
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
    public Ticket editTicket(@RequestBody Ticket editedTicket, @PathVariable Long id) {
        editedTicket.setId(id);
        return ticketRepository.save(editedTicket);
    }

    // Delete ticket
    @DeleteMapping("/{id}")
    public Iterable<Ticket> deleteTicket(@PathVariable Long id) {
        ticketRepository.deleteById(id);
        return ticketRepository.findAll();
    }
}

package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.repository.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class RestTicketController {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    // Get tickets
    @GetMapping("")
    public Iterable<Ticket> getTickets() {
        return ticketRepository.findAll();
    }
    
    // Get ticket by id
    @GetMapping("{id}")
    public Optional<Ticket> getTicket(@PathVariable("id") Long asiakasId) {
        return ticketRepository.findById(asiakasId);
    }
    
    // Post a new ticket
    @PostMapping("")
    public Ticket newTicket(@RequestBody Ticket newTicket) {
        return ticketRepository.save(newTicket);
    }
    
    // Edit ticket
    @PutMapping("{id}")
    public Ticket editTicket(@RequestBody Ticket editedTicket, @PathVariable Long id) {
        editedTicket.setId(id);
        return ticketRepository.save(editedTicket);
    }

    // Delete ticket
    @DeleteMapping("{id}")
    public Iterable<Ticket> deleteTicket(@PathVariable Long id) {
        ticketRepository.deleteById(id);
        return ticketRepository.findAll();
    }
}

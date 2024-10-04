package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.repository.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickettypes")
@Validated
public class RestTicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;

    public RestTicketTypeController(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    // Get all ticket types
    @GetMapping
    public Iterable<TicketType> getTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    // Get ticket type by id
    @GetMapping("{id}")
    public Optional<TicketType> getTicketType(@PathVariable("id") Long id) {
        return ticketTypeRepository.findById(id);
    }

    // Add a new ticket type

    // Update a ticket type

    // Delete a ticket type
}

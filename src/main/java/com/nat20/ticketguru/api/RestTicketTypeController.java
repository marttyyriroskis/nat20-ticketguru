package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.EventRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickettypes")
@Validated
public class RestTicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public RestTicketTypeController(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
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
    @PostMapping
    public TicketType createTicketType(@Valid @RequestBody TicketType newTicketType) {
        if (newTicketType.getEvent() != null) {
            Optional<Event> event = eventRepository.findById(newTicketType.getEvent().getId());

            if (!event.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event does not exist!");
            }

            newTicketType.setEvent(event.get());
        };
        
        ticketTypeRepository.save(newTicketType);
        return newTicketType;
    }

    // Update a ticket type

    // Delete a ticket type
}

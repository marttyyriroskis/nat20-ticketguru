package com.nat20.ticketguru.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nat20.ticketguru.repository.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class RestTicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;

    public RestTicketTypeController(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    // Get all ticket types

    // Get ticket type by id

    // Add a new ticket type

    // Update a ticket type

    // Delete a ticket type
}

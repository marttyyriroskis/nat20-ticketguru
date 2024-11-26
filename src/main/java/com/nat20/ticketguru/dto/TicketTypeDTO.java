package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TicketTypeDTO(
        @NotEmpty(message = "Ticket type name cannot be empty")
        @Size(min = 1, max = 100, message = "Ticket type name must be between 1 and 100 characters long")
        String name,

        @NotNull
        @Positive(message = "Ticket type price must be positive")
        double retailPrice,

        @Positive(message = "Total available must be positive or null")
        Integer totalAvailable,

        @NotNull(message = "Event must not be null")
        Long eventId,
        
        List<Long> ticketIds) {
}
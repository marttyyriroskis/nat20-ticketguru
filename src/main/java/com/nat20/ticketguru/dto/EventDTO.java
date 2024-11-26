package com.nat20.ticketguru.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EventDTO(
        @NotEmpty(message = "Event name cannot be empty")
        @Size(min = 1, max = 100, message = "Event name cannot be empty and must be between 1 and 100 characters long")
        String name,

        @NotEmpty(message = "Event description cannot be empty")
        @Size(min = 1, max = 500, message = "Event description cannot be empty and must be between 1 and 500 characters long")
        String description,

        @NotNull(message = "Total tickets cannot be null")
        @Positive(message = "Amount of tickets must be positive")
        int totalTickets,

        @NotNull(message = "Event start date cannot be null")
        @Future(message = "Event start date must be in the future")
        LocalDateTime beginsAt,

        @NotNull(message = "Event end date cannot be null")
        @Future(message = "Event end date must be in the future")
        LocalDateTime endsAt,

        LocalDateTime ticketSaleBegins,

        @NotNull(message = "Event venue id cannot be null")
        Long venueId,
        
        List<Long> ticketTypeIds) {
}

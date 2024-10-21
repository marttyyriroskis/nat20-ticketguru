package com.nat20.ticketguru.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EventDTO(
                long id,
                @NotNull(message = "Event name cannot be null") @Size(min = 1, max = 500, message = "Event name cannot be empty and must be between 1 and 100 characters long") String name,
                @NotNull(message = "Event description cannot be null") @Size(min = 1, max = 500, message = "Event description cannot be empty and must be between 1 and 500 characters long") String description,
                @Positive(message = "Amount of tickets must be positive") int total_tickets,
                @Future(message = "The event start date must be in the future") LocalDateTime begins_at,
                @Future(message = "The event end date must be in the future") LocalDateTime ends_at,
                LocalDateTime ticket_sale_begins,
                Long venueId) {
}

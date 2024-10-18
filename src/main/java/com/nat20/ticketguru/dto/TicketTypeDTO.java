package com.nat20.ticketguru.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TicketTypeDTO(
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long") String name,
        @Positive(message = "Price must be positive") double retail_price,
        @Positive(message = "Total available must be positive or null") Integer total_available,
        @NotNull(message = "Event must not be null") Long eventId) {
}
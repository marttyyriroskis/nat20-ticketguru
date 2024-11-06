package com.nat20.ticketguru.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record TicketItemDTO(
        @NotNull(message = "TicketTypeId cannot be null")
        Long ticketTypeId,
        @Positive(message = "Quantity must be positive")
        int quantity,
        @PositiveOrZero(message = "Price must be positive or zero")
        double price) {

}

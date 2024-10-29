package com.nat20.ticketguru.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketItemDTO(
        @NotNull(message = "TicketTypeId cannot be null")
        Long ticketTypeId,
        @Positive(message = "Quantity must be positive")
        int quantity,
        @NotNull(message = "Price cannot be null")
        double price) {

}

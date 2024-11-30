package com.nat20.ticketguru.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TicketDTO(
        Long id,

        String barcode,

        LocalDateTime usedAt,

        @PositiveOrZero(message = "Price must be positive or zero")
        double price,

        Long saleId,

        @NotNull
        Long ticketTypeId) {

}

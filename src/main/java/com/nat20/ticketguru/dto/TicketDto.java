package com.nat20.ticketguru.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record TicketDTO (
    @NotNull(message = "Barcode must not be null") String barcode,

    @NotNull(message = "Used At must not be null") LocalDateTime usedAt,

    @NotNull(message = "Price must not be null") double price,

    @NotNull(message = "Ticket ID must not be null") Long ticketTypeId,

    @NotNull(message = "Sale ID must not be null") Long saleId
) {}

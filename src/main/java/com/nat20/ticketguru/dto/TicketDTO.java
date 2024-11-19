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
        LocalDateTime deletedAt,
        @NotNull(message = "TicketType ID must not be null")
        Long ticketTypeId,
        Long saleId,
        TicketTypeDTO ticketType,
        EventDTO event,
        VenueDTO venue) {

}

package com.nat20.ticketguru.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

public record TicketDTO(
        @NotEmpty
        String barcode,

        LocalDateTime usedAt,

        @PositiveOrZero(message = "Price must be positive or zero")
        double price,

        Long saleId,
        TicketTypeDTO ticketType,
        EventDTO event,
        VenueDTO venue) {

}

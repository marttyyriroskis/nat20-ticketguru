package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record BasketDTO(
        @NotNull(message = "TicketItems list cannot be null")
        List<TicketItemDTO> ticketItems,
        @NotNull(message = "UserId cannot be null")
        Long userId) {

}

package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record BasketDTO(
        @NotEmpty(message = "TicketItems list cannot be empty")
        List<TicketItemDTO> ticketItems) {
}

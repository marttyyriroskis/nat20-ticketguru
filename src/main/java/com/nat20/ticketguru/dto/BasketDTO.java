package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record BasketDTO(
        @NotEmpty(message = "TicketItems list cannot be empty")
        // add nested validation with the @Valid annotation for each ticketItem
        List<@Valid TicketItemDTO> ticketItems) {

}

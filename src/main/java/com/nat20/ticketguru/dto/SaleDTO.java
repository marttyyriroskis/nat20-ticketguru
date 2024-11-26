package com.nat20.ticketguru.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public record SaleDTO(
        @NotNull(message = "Paid at cannot be null")
        LocalDateTime paidAt,

        @NotNull(message = "userId cannot be null")
        Long userId,

        @NotNull(message = "ticketIds cannot be null")
        List<Long> ticketIds) {

}

package com.nat20.ticketguru.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleDTO(
        Long id,

        @NotNull(message = "Paid at cannot be null")
        LocalDateTime paidAt,

        @NotNull(message = "userId cannot be null")
        Long userId,

        @Positive(message = "Transaction total must be positive")
        BigDecimal transactionTotal,

        @NotNull(message = "ticketIds cannot be null")
        List<Long> ticketIds) {

}

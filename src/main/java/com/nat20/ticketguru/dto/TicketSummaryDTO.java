package com.nat20.ticketguru.dto;

public record TicketSummaryDTO(
        Long ticketTypeId,
        Long eventId,
        int ticketsSold,
        int ticketsTotal,
        double totalRevenue) {

}

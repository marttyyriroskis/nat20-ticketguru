package com.nat20.ticketguru.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
// subselect instead of table prevents hibernate from trying to make it into a table which conflicts with the material view
@Subselect("SELECT * FROM event_ticket_summary")
// read-only
@Immutable
public class TicketSummary {

    @Id
    private Long ticketTypeId; // is null for event-level totals

    private Long eventId; // is null for grand totals
    private Long ticketsSold;
    private Long ticketsTotal;
    private BigDecimal totalRevenue;

    public Long getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Long getTicketsTotal() {
        return ticketsTotal;
    }

    public void setTicketsTotal(Long ticketsTotal) {
        this.ticketsTotal = ticketsTotal;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}

package com.nat20.ticketguru.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.nat20.ticketguru.dto.TicketSummaryDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entity class for TicketSummary
 * 
 * @Subselect instead of table prevents hibernate from trying to make it into a table which conflicts with the material view
 * @Immutable read-only
 * @method toDTO()
 */
@Entity
@Subselect("SELECT * FROM event_ticket_summary")
@Immutable
public class TicketSummary {

    @Id
    private Long ticketTypeId;

    private Long eventId;
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

    public TicketSummaryDTO toDTO() {
        return new TicketSummaryDTO(
                this.ticketTypeId,
                this.eventId,
                this.ticketsSold.intValue(),
                this.ticketsTotal.intValue(),
                this.totalRevenue != null ? this.totalRevenue.doubleValue() : 0.0
        );
    }
}

package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.nat20.ticketguru.dto.TicketTypeDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entity class for TicketType
 * 
 * @ManyToOne relationship to Event
 * @OneToMany relationship to Ticket
 * @method delete()
 * @method toDTO()
 */
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Positive
    @Column(name = "retail_price")
    private double retailPrice;

    @Positive
    @Column(name = "total_tickets")
    private Integer totalTickets;

    @Positive
    private Integer availableTickets;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketType")
    private List<Ticket> tickets;

    public TicketType() {
    }

    public TicketType(String name, double retailPrice, Integer totalTickets, Event event) {
        this.name = name;
        this.retailPrice = retailPrice;
        this.totalTickets = totalTickets;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public TicketTypeDTO toDTO() {
        return new TicketTypeDTO(
                this.id,
                this.name,
                this.retailPrice,
                this.totalTickets,
                null,
                this.event.getId(),
                this.tickets == null
                        ? Collections.emptyList()
                        : this.tickets.stream()
                                .map(Ticket::getId)
                                .collect(Collectors.toList())
        );
    }

    // overloaded method
    public TicketTypeDTO toDTO(Integer availableTickets) {
        return new TicketTypeDTO(
                this.id,
                this.name,
                this.retailPrice,
                this.totalTickets,
                availableTickets,
                this.event.getId(),
                this.tickets == null
                        ? Collections.emptyList()
                        : this.tickets.stream()
                                .map(Ticket::getId)
                                .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return "{"
                + " id='" + getId() + "'"
                + ", name='" + getName() + "'"
                + ", retailPrice='" + getRetailPrice() + "'"
                + ", totalAvailable='" + getTotalTickets() + "'"
                + ", event='" + getEvent() + "'"
                + ", deletedAt='" + getDeletedAt() + "'"
                + "}";
    }
}

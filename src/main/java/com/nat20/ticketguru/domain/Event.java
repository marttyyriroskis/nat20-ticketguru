package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Event name cannot be null")
    @Size(min = 1, max = 500, message = "Event name cannot be empty and must be between 1 and 100 characters long")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Event description cannot be null")
    @Size(min = 1, max = 500, message = "Event description cannot be empty and must be between 1 and 500 characters long")
    @Column(name = "description")
    private String description;

    @Positive(message = "Amount of tickets must be positive")
    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @NotNull(message = "Event start date cannot be null")
    @Future(message = "Event start date must be in the future")
    @Column(name = "begins_at")
    private LocalDateTime beginsAt;

    @NotNull(message = "Event end date cannot be null")
    @Future(message = "Event end date must be in the future")
    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @Column(name = "ticket_sale_begins")
    private LocalDateTime ticketSaleBegins;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = true)
    private Venue venue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonIgnore
    private List<TicketType> ticketType;

    public Event() {
    }

    public Event(String name, String description, int totalTickets, LocalDateTime beginsAt,
            LocalDateTime endsAt, LocalDateTime ticketSaleBegins, Venue venue) {
        this.name = name;
        this.description = description;
        this.totalTickets = totalTickets;
        this.beginsAt = beginsAt;
        this.endsAt = endsAt;
        this.ticketSaleBegins = ticketSaleBegins;
        this.venue = venue;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public LocalDateTime getBeginsAt() {
        return this.beginsAt;
    }

    public void setBeginsAt(LocalDateTime beginsAt) {
        this.beginsAt = beginsAt;
    }

    public LocalDateTime getEndsAt() {
        return this.endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public LocalDateTime getTicketSaleBegins() {
        return this.ticketSaleBegins;
    }

    public void setTicketSaleBegins(LocalDateTime ticketSaleBegins) {
        this.ticketSaleBegins = ticketSaleBegins;
    }

    public Venue getVenue() {
        return this.venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "{"
                + " id='" + getId() + "'"
                + ", name='" + getName() + "'"
                + ", description='" + getDescription() + "'"
                + ", totalTickets='" + getTotalTickets() + "'"
                + ", beginsAt='" + getBeginsAt() + "'"
                + ", endsAt='" + getEndsAt() + "'"
                + ", ticketSaleBegins='" + getTicketSaleBegins() + "'"
                + ", venue='" + getVenue() + "'"
                + "}";
    }
}

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
import jakarta.validation.constraints.Size;

import com.nat20.ticketguru.dto.EventDTO;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 1, max = 500)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @Column(name = "begins_at", nullable = false)
    @Future
    private LocalDateTime beginsAt;

    @Column(name = "ends_at", nullable = false)
    @Future
    private LocalDateTime endsAt;

    @Column(name = "ticket_sale_begins", nullable = true)
    private LocalDateTime ticketSaleBegins;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = true)
    private Venue venue;

    @Column(name = "deletedAt", nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonIgnore
    private List<TicketType> ticketType;

    public Event() {
    }

    public Event(String name, String description, int totalTickets, LocalDateTime beginsAt,
            LocalDateTime endsAt, LocalDateTime ticketSaleBegins, Venue venue, LocalDateTime deletedAt) {
        this.name = name;
        this.description = description;
        this.totalTickets = totalTickets;
        this.beginsAt = beginsAt;
        this.endsAt = endsAt;
        this.ticketSaleBegins = ticketSaleBegins;
        this.venue = venue;
        this.deletedAt = deletedAt;
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

    public LocalDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedAt = null;
    }

    public EventDTO toDTO() {
        return new EventDTO(
                this.id,
                this.name,
                this.description,
                this.totalTickets,
                this.beginsAt,
                this.endsAt,
                this.ticketSaleBegins,
                this.venue.getId());
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
                + ", deletedAt='" + getDeletedAt() + "'"
                + "}";
    }
}

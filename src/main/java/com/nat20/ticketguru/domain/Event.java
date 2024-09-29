package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive(message = "Total tickets must be positive")
    @Column(name = "total_tickets", nullable = false)
    private int total_tickets;

    @FutureOrPresent(message = "Begin date must be future or present")
    @Column(name = "begins_at")
    private LocalDateTime begins_at;

    @FutureOrPresent(message = "End date must be future or present")
    @Column(name = "ends_at")
    private LocalDateTime ends_at;

    @FutureOrPresent(message = "Ticket sale begin date must be future or present")
    @Column(name = "ticket_sale_begins")
    private LocalDateTime ticket_sale_begins;

    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters long")
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "venue_id")
    private Venue venue;

    public Event() {
    }

    public Event(String name, String description, int total_tickets, LocalDateTime begins_at,
            LocalDateTime ends_at, LocalDateTime ticket_sale_begins, Venue venue) {
        this.name = name;
        this.description = description;
        this.total_tickets = total_tickets;
        this.begins_at = begins_at;
        this.ends_at = ends_at;
        this.ticket_sale_begins = ticket_sale_begins;
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

    public int getTotal_tickets() {
        return this.total_tickets;
    }

    public void setTotal_tickets(int total_tickets) {
        this.total_tickets = total_tickets;
    }

    public LocalDateTime getBegins_at() {
        return this.begins_at;
    }

    public void setBegins_at(LocalDateTime begins_at) {
        this.begins_at = begins_at;
    }

    public LocalDateTime getEnds_at() {
        return this.ends_at;
    }

    public void setEnds_at(LocalDateTime ends_at) {
        this.ends_at = ends_at;
    }

    public LocalDateTime getTicket_sale_begins() {
        return this.ticket_sale_begins;
    }

    public void setTicket_sale_begins(LocalDateTime ticket_sale_begins) {
        this.ticket_sale_begins = ticket_sale_begins;
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
                + ", total_tickets='" + getTotal_tickets() + "'"
                + ", begins_at='" + getBegins_at() + "'"
                + ", ends_at='" + getEnds_at() + "'"
                + ", ticket_sale_begins='" + getTicket_sale_begins() + "'"
                + ", venue='" + getVenue() + "'"
                + "}";
    }
}

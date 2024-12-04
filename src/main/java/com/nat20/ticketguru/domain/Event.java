package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.nat20.ticketguru.dto.EventDTO;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entity class for Event
 * 
 * @ManyToOne relationship to Venue
 * @OneToMany relationship to TicketType
 * @method delete()
 * @method toDTO()
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    @Positive
    @Column(name = "total_tickets")
    private int totalTickets;

    @NotNull
    @Future
    @Column(name = "begins_at")
    private LocalDateTime beginsAt;

    @NotNull
    @Future
    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @Column(name = "ticket_sale_begins")
    private LocalDateTime ticketSaleBegins;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<TicketType> ticketTypes = new ArrayList<>();

    public Event() {
    }

    public Event(String name, String description, int totalTickets, LocalDateTime beginsAt, LocalDateTime endsAt) {
        this.name = name;
        this.description = description;
        this.totalTickets = totalTickets;
        this.beginsAt = beginsAt;
        this.endsAt = endsAt;
    }

    public Event(String name, String description, int totalTickets, LocalDateTime beginsAt, LocalDateTime endsAt, Venue venue) {
        this.name = name;
        this.description = description;
        this.totalTickets = totalTickets;
        this.beginsAt = beginsAt;
        this.endsAt = endsAt;
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

    public LocalDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
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
                this.venue.getId(),
                this.ticketTypes == null
                        ? Collections.emptyList()
                        : this.ticketTypes.stream()
                                .map(TicketType::toDTO)
                                .collect(Collectors.toList())
        );
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

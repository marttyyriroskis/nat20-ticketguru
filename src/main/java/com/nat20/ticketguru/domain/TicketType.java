package com.nat20.ticketguru.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import com.nat20.ticketguru.dto.TicketTypeDTO;

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
    @Column(name = "total_available")
    private Integer totalAvailable;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket_type")
    @JsonIgnore
    private List<Ticket> tickets;

    public TicketType() {
    }

    public TicketType(String name, double retailPrice, Integer totalAvailable, Event event) {
        this.name = name;
        this.retailPrice = retailPrice;
        this.totalAvailable = totalAvailable;
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

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
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
            this.name,
            this.retailPrice,
            this.totalAvailable,
            this.event.getId(),

            this.tickets.stream()
                    .map(Ticket::getId)
                    .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", retailPrice='" + getRetailPrice() + "'" +
            ", totalAvailable='" + getTotalAvailable() + "'" +
            ", event='" + getEvent() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}

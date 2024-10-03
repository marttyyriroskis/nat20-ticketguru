package com.nat20.ticketguru.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "ticket_types")
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive(message = "Price must be positive")
    @Column(name = "retail_price", nullable = false)
    private double retail_price;

    @PositiveOrZero(message = "Total available must be positive or zero")
    @Column(name = "total_available", nullable = false)
    private int total_available;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket_type")
    @JsonIgnore
    private List<Ticket> tickets;

    public TicketType() {
    }

    public TicketType(String name, double retail_price, int total_available, Event event) {
        this.name = name;
        this.retail_price = retail_price;
        this.total_available = total_available;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(double retail_price) {
        this.retail_price = retail_price;
    }

    public int getTotal_available() {
        return total_available;
    }

    public void setTotal_available(int total_available) {
        this.total_available = total_available;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "TicketType [id=" + id + ", name=" + name + ", retail_price=" + retail_price + ", total_available="
                + total_available + ", event=" + event + "]";
    }

}

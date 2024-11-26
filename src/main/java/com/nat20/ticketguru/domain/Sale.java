package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nat20.ticketguru.dto.SaleDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "paid_at", updatable = false)
    private LocalDateTime paidAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Only allow deserialization, not serialization
    private User user;

    // userId is not persistent
    @Transient
    private Long userId;

    @NotNull
    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Ticket> tickets = new ArrayList<>();

    private LocalDateTime deletedAt;

    public Sale() {
        this.paidAt = LocalDateTime.now();
    }

    public Sale(LocalDateTime paidAt, List<Ticket> tickets, User user) {
        this.paidAt = paidAt;
        this.tickets = tickets;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user != null ? user.getId() : null; // Return the user ID if user is not null
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public SaleDTO toDTO() {
        return new SaleDTO(
            this.paidAt,
            this.user.getId(),

            this.tickets.stream()
                .map(Ticket::getId)
                .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return "Sale [id=" + id + ", paidAt=" + paidAt + ", tickets=" + tickets + ", userId=" + userId + "]";
    }
}

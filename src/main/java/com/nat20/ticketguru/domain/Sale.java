package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@CreatedDate //needs JPA auditing to work. lets set the time manually for now
    @Column(name = "paid_at", updatable = false, nullable = false)
    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Only allow deserialization, not serialization
    private User user;

    // userId is not persistent
    @Transient
    private Long userId;

    public Long getUserId() {
        return user != null ? user.getId() : null; // Return the user ID if user is not null
    }

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Ticket> tickets = new ArrayList<>();

    public Sale() {
        this.paidAt = LocalDateTime.now();
    }

    public Sale(LocalDateTime paidAt, List<Ticket> tickets, User user) {
        this.paidAt = paidAt; // allows manually setting the time
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Sale [id=" + id + ", paidAt=" + paidAt + ", tickets=" + tickets + ", userId=" + userId + "]";
    }

}

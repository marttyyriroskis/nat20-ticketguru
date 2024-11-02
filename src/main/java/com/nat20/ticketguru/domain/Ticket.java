package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nat20.ticketguru.dto.TicketDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    //Size annotation if the barcode should always be the same length
    private String barcode;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @PositiveOrZero
    private double price;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @JsonIgnore
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnore
    private Sale sale;

    public Ticket() {
        // Generate barcode from timestamp
        this.barcode = UUID.randomUUID().toString();
    }

    public Ticket(String eventCode) { // TODO: Figure out how the barcode should be generated
        // Generate barcode from timestamp and event code
        // Event code = event id?
        this.barcode = eventCode + "-" + UUID.randomUUID().toString();
    }

    public Ticket(LocalDateTime usedAt, double price, LocalDateTime deletedAt,
            TicketType ticketType, Sale sale) {
        this.barcode = UUID.randomUUID().toString();
        this.usedAt = usedAt;
        this.price = price;
        this.deletedAt = deletedAt;
        this.ticketType = ticketType;
        this.sale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /* TODO: Implement markAsUsed() method, e.g.:
        public void markAsUsed() {
        this.usedAt = LocalDateTime.now();
        }
     */
    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", barcode=" + barcode + ", usedAt=" + usedAt + ", price=" + price + ", deletedAt="
                + deletedAt + "]";
    }

    public TicketDTO toDTO() {
        return new TicketDTO(id, barcode, usedAt, price, deletedAt, ticketType.getId(), sale.getId());
    }

}

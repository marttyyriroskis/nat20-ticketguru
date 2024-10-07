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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Barcode must not be empty")
    //Size annotation if the barcode should always be the same length
    @Column(name = "barcode", updatable = false)
    private String barcode;

    @Column(name = "used_at", updatable = false)
    private LocalDateTime usedAt;

    @PositiveOrZero(message = "Price must be positive or zero")
    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @JsonIgnore
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnore // prevent infinite loops
    private Sale sale;

    public Ticket() {
        // Generate barcode from timestamp
        this.barcode = String.valueOf(System.currentTimeMillis());
    }

    public Ticket(String eventCode) { // TODO: Figure out how the barcode should be generated
        // Generate barcode from timestamp and event code
        // Event code = event id?
        this.barcode = eventCode + "-" + System.currentTimeMillis();
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

    // ticketTypeId is not persistent
    @Transient
    private Long ticketTypeId;

    public Long getTicketTypeId() {
        return ticketType != null ? ticketType.getId() : null; // Return the ticketType ID if ticketType is not null
    }

    // saleId is not persistent
    @Transient
    private Long saleId;

    public Long getSaleId() {
        return sale != null ? sale.getId() : null; // Return the sale ID if sale is not null
    }

    // omit sale and ticketType from toString to prevent infinite loops, use saleId and ticketTypeId instead
    @Override
    public String toString() {
        return "Ticket [id=" + id + ", barcode=" + barcode + ", usedAt=" + usedAt + ", price=" + price + ", ticketTypeId="
                + ticketTypeId + ", saleId=" + saleId + "]";
    }

}

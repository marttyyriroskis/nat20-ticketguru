package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.nat20.ticketguru.dto.TicketDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Entity class for Ticket
 * 
 * @ManyToOne relationship to TicketType
 * @ManyToOne relationship to Sale
 * @method delete()
 * @method toDTO()
 * @method generateBarcode()
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    public Ticket() {
        this.barcode = generateBarcode();
    }

    public Ticket(String eventCode) {
        this.barcode = generateBarcode(eventCode);
    }

    public Ticket(double price, TicketType ticketType, Sale sale) {
        this.barcode = generateBarcode();
        this.price = price;
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

    public Ticket use() {
        this.usedAt = LocalDateTime.now();
        return this;
    }

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
        return new TicketDTO(
                this.id,
                this.barcode,
                this.usedAt,
                this.price,
                this.sale.getId(),
                this.ticketType.getId() //, ticketType.getEvent().toDTO(), ticketType.getEvent().getVenue().toDTO()
        );
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString() + System.currentTimeMillis();
    }

    private String generateBarcode(String eventCode) {
        return eventCode + generateBarcode();
    }

}

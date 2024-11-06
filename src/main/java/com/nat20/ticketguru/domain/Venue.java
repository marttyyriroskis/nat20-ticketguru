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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Address must not be empty")
    @Size(min = 1, max = 100)
    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "zipcode", nullable = false)
    private Zipcode zipcode;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
    @JsonIgnore
    private List<Event> events;

    public Venue() {
    }

    public Venue(String name, String address, Zipcode zipcode, LocalDateTime deletedAt) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.deletedAt = deletedAt;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Zipcode getZipcode() {
        return zipcode;
    }

    public void setZipcode(Zipcode zipcode) {
        this.zipcode = zipcode;
    }

     public LocalDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}

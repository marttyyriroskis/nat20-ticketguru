package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.nat20.ticketguru.dto.VenueDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entity class for Venue
 * 
 * @ManyToOne relationship to Zipcode
 * @OneToMany relationship to Event
 * @method delete()
 * @method toDTO()
 */
@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String address;

    @NotNull
    @ManyToOne
    private Zipcode zipcode;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
    private List<Event> events;

    public Venue() {
    }

    public Venue(String name, String address, Zipcode zipcode) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
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

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public VenueDTO toDTO() {
        return new VenueDTO(
                this.id,
                this.name,
                this.address,
                this.zipcode.getZipcode(),
                this.events == null
                        ? Collections.emptyList()
                        : this.events.stream()
                                .map(Event::getId)
                                .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return "Venue [id=" + id + ", name=" + name + ", address=" + address + ", zipcode=" + zipcode + ", deletedAt="
                + deletedAt + ", events=" + events + "]";
    }

}

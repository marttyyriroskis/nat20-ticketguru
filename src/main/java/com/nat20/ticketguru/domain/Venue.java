package com.nat20.ticketguru.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Venue {

    @Id
    private Long id;

    public Venue() {
    }

    public Venue(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

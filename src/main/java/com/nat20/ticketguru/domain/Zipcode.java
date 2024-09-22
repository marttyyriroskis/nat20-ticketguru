package com.nat20.ticketguru.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "zipcodes")
public class Zipcode {

    @Id
    private String zipcode;
    private String city;

    public Zipcode() {}

    public Zipcode(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    // Getters and setters
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
}

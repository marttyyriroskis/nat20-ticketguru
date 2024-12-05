package com.nat20.ticketguru.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Entity class for Zipcode
 */
@Entity
@Table(name = "zipcodes")
public class Zipcode {

    @Id
    @Size(max = 5)
    private String zipcode;

    @NotEmpty
    @Size(max = 100)
    private String city;

    public Zipcode() {}

    public Zipcode(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

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

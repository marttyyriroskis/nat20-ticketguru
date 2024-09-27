package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Zipcode;

public interface ZipcodeRepository extends CrudRepository<Zipcode, String> {
    Zipcode findByZipcode(String zipcode);
}

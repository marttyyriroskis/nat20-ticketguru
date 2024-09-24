package com.nat20.ticketguru.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VenueRepository extends CrudRepository<Venue, Long> {

    List<Venue> findByName(String name);

}
package com.nat20.ticketguru.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Venue;

public interface VenueRepository extends CrudRepository<Venue, Long> {

    List<Venue> findByName(String name);

}
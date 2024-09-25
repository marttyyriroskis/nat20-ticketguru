package com.nat20.ticketguru.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findByName(String name);
}

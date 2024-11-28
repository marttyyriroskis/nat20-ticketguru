package com.nat20.ticketguru.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE deleted_at IS NULL", nativeQuery = true)
    List<Event> findAllActive();

    @Query(value = "SELECT * FROM events WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Event> findByIdActive(@Param("id") Long id);

    List<Event> findByName(String name);
}

package com.nat20.ticketguru.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Venue;

public interface VenueRepository extends CrudRepository<Venue, Long> {

    @Query(value = "SELECT * FROM venues WHERE deleted_at IS NULL", nativeQuery = true)
    List<Venue> findAllActive();

    @Query(value = "SELECT * FROM venues WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Venue> findByIdActive(@Param("id") Long id);

    List<Venue> findByName(String name);
}
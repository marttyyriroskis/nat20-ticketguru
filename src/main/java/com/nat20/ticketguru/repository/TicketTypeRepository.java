package com.nat20.ticketguru.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {

    @Query(value = "SELECT * FROM ticket_types WHERE deleted_at IS NULL", nativeQuery = true)
    List<TicketType> findAllActive();

    @Query(value = "SELECT * FROM ticket_types WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<TicketType> findByIdActive(@Param("id") Long id);

    @Query(value = "SELECT * FROM ticket_types WHERE event_id = :event_id AND deleted_at IS NULL", nativeQuery = true)
    List<TicketType> findByEventIdActive(@Param("event_id") Long eventId);
    List<TicketType> findByEvent(Event event);

}

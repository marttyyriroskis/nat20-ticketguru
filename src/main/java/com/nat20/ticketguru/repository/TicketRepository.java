package com.nat20.ticketguru.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query(value = "SELECT * FROM tickets WHERE deleted_at IS NULL", nativeQuery = true)
    List<Ticket> findAllActive();

    @Query(value = "SELECT * FROM tickets WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Ticket> findByIdActive(@Param("id") Long id);

    Ticket findByBarcode(String barcode);

    List<Ticket> findAllByIdIn(List<Long> ids);

}

package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}

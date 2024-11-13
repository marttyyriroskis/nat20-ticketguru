package com.nat20.ticketguru.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {

    List<TicketType> findByEvent(Event event);

}

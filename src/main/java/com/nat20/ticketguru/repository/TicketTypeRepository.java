package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.*;

public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {

}

package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.TicketSale;

public interface TicketSaleRepository extends CrudRepository<TicketSale, Long> {

}

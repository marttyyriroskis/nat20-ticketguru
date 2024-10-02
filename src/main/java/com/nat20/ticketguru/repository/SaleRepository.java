package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Sale;

public interface SaleRepository extends CrudRepository<Sale, Long> {
}

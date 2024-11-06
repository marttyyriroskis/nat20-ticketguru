package com.nat20.ticketguru.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.User;

public interface SaleRepository extends CrudRepository<Sale, Long> {

    List<Sale> findByPaidAtBetween(LocalDateTime start, LocalDateTime end);

    List<Sale> findByPaidAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

    List<Sale> findByPaidAtAfter(LocalDateTime start);

    List<Sale> findByPaidAtBefore(LocalDateTime end);

    List<Sale> findByPaidAtAfterAndUser(LocalDateTime start, User user);

    List<Sale> findByPaidAtBeforeAndUser(LocalDateTime end, User user);

    List<Sale> findByUser(User user);

}

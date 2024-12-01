package com.nat20.ticketguru.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.User;

public interface SaleRepository extends CrudRepository<Sale, Long> {

    @Query(value = "SELECT * FROM sales WHERE deleted_at IS NULL", nativeQuery = true)
    List<Sale> findAllActive();

    @Query(value = "SELECT * FROM sales WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Sale> findByIdActive(@Param("id") Long id);

    List<Sale> findByPaidAtBetween(LocalDateTime start, LocalDateTime end);

    List<Sale> findByPaidAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

    List<Sale> findByPaidAtAfter(LocalDateTime start);

    List<Sale> findByPaidAtBefore(LocalDateTime end);

    List<Sale> findByPaidAtAfterAndUser(LocalDateTime start, User user);

    List<Sale> findByPaidAtBeforeAndUser(LocalDateTime end, User user);

    List<Sale> findByUser(User user);

    List<Sale> findByEventId(Long eventId);

}

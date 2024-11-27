package com.nat20.ticketguru.repository;

import java.util.Optional;

import com.nat20.ticketguru.domain.TicketSummary;
import java.util.List;


public interface TicketSummaryRepository extends ReadOnlyRepository<TicketSummary, Long> {

    Optional<TicketSummary> findByTicketTypeId(Long ticketTypeId);

    List<TicketSummary> findByEventId(Long eventId);
}

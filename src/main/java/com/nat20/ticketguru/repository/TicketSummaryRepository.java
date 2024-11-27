package com.nat20.ticketguru.repository;

import java.util.Optional;

import com.nat20.ticketguru.domain.TicketSummary;

public interface TicketSummaryRepository extends ReadOnlyRepository<TicketSummary, Long> {

    Optional<TicketSummary> findByTicketTypeId(Long ticketTypeId);
}

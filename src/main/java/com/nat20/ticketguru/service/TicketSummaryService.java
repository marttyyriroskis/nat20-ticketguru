package com.nat20.ticketguru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketSummary;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.TicketSummaryRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;

@Service
public class TicketSummaryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TicketSummaryRepository ticketSummaryRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    public void refreshMateralizedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY event_ticket_summary");
    }

    public int countAvailableTickets(Long ticketTypeId) {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketType not found"));

        TicketSummary summary = ticketSummaryRepository.findByTicketTypeId(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketType not found"));

        Event event = eventRepository.findById(ticketType.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        int totalAvailable;

        if (ticketType.getTotalAvailable() == null) {
            totalAvailable = event.getTotalTickets();
        } else {
            totalAvailable = ticketType.getTotalAvailable();
        }

        return totalAvailable - summary.getTicketsSold().intValue();
    }

}

package com.nat20.ticketguru.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketSummary;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.TicketTypeDTO;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.TicketSummaryRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;

@Service
public class TicketSummaryService {

    private final JdbcTemplate jdbcTemplate;
    private final TicketSummaryRepository ticketSummaryRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public TicketSummaryService(JdbcTemplate jdbcTemplate, TicketSummaryRepository ticketSummaryRepository,
            TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.ticketSummaryRepository = ticketSummaryRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }

    public void refreshMateralizedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY event_ticket_summary");
    }

    public int countAvailableTicketsForTicketType(Long ticketTypeId) {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketType not found"));

        refreshMateralizedView();

        TicketSummary summary = ticketSummaryRepository.findByTicketTypeId(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketSummary not found"));

        int eventAvailable = countAvailableTicketsForEvent(summary.getEventId());

        if (ticketType.getTotalTickets() != null) {
            return Math.min(eventAvailable, ticketType.getTotalTickets() - summary.getTicketsSold().intValue());
        }

        return eventAvailable;
    }

    public int countAvailableTicketsForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        refreshMateralizedView();

        List<TicketSummary> ticketSummaries = ticketSummaryRepository.findByEventId(eventId);

        // No tickets have been sold, so all tickets are available
        if (ticketSummaries.isEmpty()) {
            return event.getTotalTickets();
        }
        int totalSold = ticketSummaries.stream()
                .mapToInt(summary -> summary.getTicketsSold().intValue()).sum();
        return event.getTotalTickets() - totalSold;
    }

    public TicketTypeDTO toDTOWithAvailableTickets(TicketType ticketType) {
        Integer availableTickets = countAvailableTicketsForTicketType(ticketType.getId());
        return ticketType.toDTO(availableTickets);
    }

    public List<TicketSummary> generateSalesReport() {
        refreshMateralizedView();
        return ticketSummaryRepository.findAll();
    }
}

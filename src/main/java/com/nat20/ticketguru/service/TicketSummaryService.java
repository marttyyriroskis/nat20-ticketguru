package com.nat20.ticketguru.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketSummary;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.TicketTypeDTO;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.TicketSummaryRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;

/**
 * Service class for TicketSummary
 * 
 * @author Paul Carlson
 * @version 1.0
 */
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

    /**
     * Refreshes the materialized view `event_ticket_summary` to update ticket data
     */
    public void refreshMateralizedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY event_ticket_summary");
    }

    /**
     * Calculates the number of available tickets for a specific ticket type
     * 
     * @param ticketTypeId the ID of the ticket type to check availability for
     * @return the number of available tickets for the specified ticket type
     * @throws IllegalArgumentException if the ticket type or its summary cannot be found
     */
    public int countAvailableTicketsForTicketType(Long ticketTypeId) {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketType not found"));

        refreshMateralizedView();

        TicketSummary summary = ticketSummaryRepository.findByTicketTypeId(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketSummary not found"));

        int eventAvailable = countAvailableTicketsForEvent(summary.getEventId());

        if (ticketType.getTotalTickets() != null) {
            return Math.min(eventAvailable, ticketType.getTotalTickets() - summary.getTicketsTotal().intValue());
        }

        return eventAvailable;
    }

    /**
     * Calculates the number of available tickets for an entire event.
     * Considers the total tickets allocated to the event and subtracts the tickets sold.
     * 
     * @param eventId the ID of the event to check ticket availability for
     * @return the number of available tickets for the specified event
     * @throws IllegalArgumentException if the event cannot be found
     */
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
                .mapToInt(summary -> summary.getTicketsTotal().intValue()).sum();
        return event.getTotalTickets() - totalSold;
    }

    /**
     * Converts a `TicketType` entity to a `TicketTypeDTO`, including the number of available tickets
     * 
     * @param ticketType the ticket type to convert
     * @return a DTO containing ticket type data and the number of available tickets
     */
    public TicketTypeDTO toDTOWithAvailableTickets(TicketType ticketType) {
        Integer availableTickets = countAvailableTicketsForTicketType(ticketType.getId());
        return ticketType.toDTO(availableTickets);
    }

    /**
     * Generates a sales report by retrieving all ticket summaries from the database.
     * Refreshes the materialized view before fetching the data to ensure up-to-date results.
     * 
     * @return a list of ticket summaries representing the sales report
     */
    public List<TicketSummary> generateSalesReport() {
        refreshMateralizedView();
        return ticketSummaryRepository.findAll();
    }

    /**
     * Checks whether a ticket type has sold any non-deleted tickets.
     * Refreshes the materialized view before the check.
     * 
     * @param ticketTypeId the ID of the ticket type to check
     * @return true if the ticket type has sold non-deleted tickets, false otherwise
     * @throws IllegalArgumentException if the ticket type cannot be found
     */
    public boolean hasSoldNonDeletedTickets(Long ticketTypeId) {
        refreshMateralizedView();
        ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("TicketType not found"));

        Optional<TicketSummary> summary = ticketSummaryRepository.findByTicketTypeId(ticketTypeId);
        // no summary = no tickets sold
        return summary.isPresent() && summary.get().getTicketsSold() != 0;
    }

    /**
     * Checks whether an event has sold any non-deleted tickets.
     * Refreshes the materialized view before the check.
     * 
     * @param eventId the ID of the event to check
     * @return true if the event has sold non-deleted tickets, false otherwise
     * @throws IllegalArgumentException if the event cannot be found
     */
    public boolean eventHasSoldNonDeletedTickets(Long eventId) {
        refreshMateralizedView();
        eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        List<TicketSummary> summaries = ticketSummaryRepository.findByEventId(eventId);
        // if stream is empty, or any tickets are sold, return false
        return summaries.stream().anyMatch(summary -> summary.getTicketsSold() != 0);
    }

}

package com.nat20.ticketguru.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.dto.EventDTO;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.service.TicketSummaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
@Validated
public class EventRestController {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TicketSummaryService ticketSummaryService;

    public EventRestController(EventRepository eventRepository, VenueRepository venueRepository, TicketSummaryService ticketSummaryService) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.ticketSummaryService = ticketSummaryService;
    }

    // Get events
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_EVENTS')")
    public ResponseEntity<List<EventDTO>> getAllEvents() {

        List<Event> events = eventRepository.findAllActive();

        return ResponseEntity.ok(events.stream()
                .map(Event::toDTO)
                .toList());
    }

    // Get event by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_EVENTS')")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {

        Event event = eventRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        return ResponseEntity.ok(event.toDTO());
    }

    // Post a new event
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_EVENTS')")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {

        Venue venue = Optional.ofNullable(eventDTO.venueId())
                .flatMap(venueRepository::findByIdActive)
                .orElse(null);

        Event event = new Event();
        event.setName(eventDTO.name());
        event.setDescription(eventDTO.description());
        event.setTotalTickets(eventDTO.totalTickets());
        event.setBeginsAt(eventDTO.beginsAt());
        event.setEndsAt(eventDTO.endsAt());
        event.setTicketSaleBegins(eventDTO.ticketSaleBegins());
        event.setVenue(venue);

        Event addedEvent = eventRepository.save(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedEvent.toDTO());
    }

    // Edit event with PUT request
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_EVENTS')")
    public ResponseEntity<EventDTO> editEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable Long id) {

        Event event = eventRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        event.setName(eventDTO.name());
        event.setDescription(eventDTO.description());
        event.setTotalTickets(eventDTO.totalTickets());
        event.setBeginsAt(eventDTO.beginsAt());
        event.setEndsAt(eventDTO.endsAt());
        event.setTicketSaleBegins(eventDTO.ticketSaleBegins());

        Venue venue = venueRepository.findByIdActive(eventDTO.venueId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Venue ID"));

        event.setVenue(venue);

        Event editedEvent = eventRepository.save(event);

        return ResponseEntity.ok(editedEvent.toDTO());
    }

    // Delete event with DELETE Request
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_EVENTS')")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {

        Event event = eventRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // check if event has any sold non-deleted tickets
        if (ticketSummaryService.eventHasSoldNonDeletedTickets(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete event because it has sold tickets.");
        }

        event.delete();

        eventRepository.save(event);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package com.nat20.ticketguru.api;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.dto.EventDTO;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.VenueRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
@Validated
public class EventRestController {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventRestController(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    // Get events
    @GetMapping("")
    public ResponseEntity<List<EventDTO>> getAllEvents() {

        List<Event> events = new ArrayList<Event>();
        eventRepository.findAll().forEach(events::add);

        List<EventDTO> eventDTOs = events.stream()
                .filter(event -> event.getDeletedAt() == null)
                .map(event -> event.toDTO())
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOs);
    }

    // Get event by id
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long eventId) {

        Optional<Event> event = eventRepository.findById(eventId);

        if (!event.isPresent() || event.get().getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        EventDTO eventDTO = event.get().toDTO();
        return ResponseEntity.ok(eventDTO);
    }

    // Post a new event
    @PostMapping("")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {

        Optional<Venue> existingVenue = venueRepository.findById(eventDTO.venueId());

        if (!existingVenue.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue does not exist!");
        }

        Event event = new Event(
                eventDTO.name(),
                eventDTO.description(),
                eventDTO.totalTickets(),
                eventDTO.beginsAt(),
                eventDTO.endsAt(),
                eventDTO.ticketSaleBegins(),
                existingVenue.get(),
                null);

        Event savedEvent = eventRepository.save(event);

        EventDTO responseDTO = savedEvent.toDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Edit event with PUT request
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> editEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable("id") Long eventId) {

        Optional<Event> existingEvent = eventRepository.findById(eventId);
        Optional<Venue> existingVenue = venueRepository.findById(eventDTO.venueId());

        if (!existingEvent.isPresent() || existingEvent.get().getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        if (!existingVenue.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
        }

        Event editedEvent = existingEvent.get();
        editedEvent.setName(eventDTO.name());
        editedEvent.setDescription(eventDTO.description());
        editedEvent.setTotalTickets(eventDTO.totalTickets());
        editedEvent.setBeginsAt(eventDTO.beginsAt());
        editedEvent.setEndsAt(eventDTO.endsAt());
        editedEvent.setTicketSaleBegins(eventDTO.ticketSaleBegins());
        editedEvent.setVenue(existingVenue.get());

        Event savedEvent = eventRepository.save(editedEvent);

        EventDTO responseDTO = savedEvent.toDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Delete event with DELETE Request
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long eventId) {

        Optional<Event> existingEvent = eventRepository.findById(eventId);

        if (!existingEvent.isPresent() || existingEvent.get().getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        existingEvent.get().setDeletedAt(LocalDateTime.now());
        
        eventRepository.save(existingEvent.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Exception handler for validation errors
    // If validation fails, a MethodArgumentNotValidException is thrown,
    // which then returns the failed field(s) and the validation failure message(s)
    // as a BAD_REQUEST response
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
    // Source:
    // https://dev.to/shujaat34/exception-handling-and-validation-in-spring-boot-3of9
    // The source details a Global Exception handler, that we could implement later
    // to handle all the endpoints
}

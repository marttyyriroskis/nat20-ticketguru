package com.nat20.ticketguru.api;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class RestEventController {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public RestEventController(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    // Get events
    @GetMapping("")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        eventRepository.findAll().forEach(events::add);

        List<EventDTO> eventDTOs = events.stream()
                .map(event -> new EventDTO(
                        event.getId(),
                        event.getName(),
                        event.getDescription(),
                        event.getTotal_tickets(),
                        event.getBegins_at(),
                        event.getEnds_at(),
                        event.getTicket_sale_begins(),
                        event.getVenue().getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOs);
    }

    // Get event by id
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        EventDTO eventDTO = new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getTotal_tickets(),
                event.getBegins_at(),
                event.getEnds_at(),
                event.getTicket_sale_begins(),
                event.getVenue().getId());

        return ResponseEntity.ok(eventDTO);
    }

    // Post a new event
    @PostMapping("")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {

        Optional<Venue> existingVenue = venueRepository.findById(eventDTO.venueId());
        if (!existingVenue.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue does not exist!");
        } else {
            Event event = new Event(
                    eventDTO.name(),
                    eventDTO.description(),
                    eventDTO.total_tickets(),
                    eventDTO.begins_at(),
                    eventDTO.ends_at(),
                    eventDTO.ticket_sale_begins(),
                    existingVenue.get());

            Event savedEvent = eventRepository.save(event);

            EventDTO responseDTO = new EventDTO(
                    savedEvent.getId(),
                    savedEvent.getName(),
                    savedEvent.getDescription(),
                    savedEvent.getTotal_tickets(),
                    savedEvent.getBegins_at(),
                    savedEvent.getEnds_at(),
                    savedEvent.getTicket_sale_begins(),
                    savedEvent.getVenue().getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
    }

    // Edit event with PUT request
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> editEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable("id") Long eventId) {

        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (!existingEvent.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        } else {
            Optional<Venue> existingVenue = venueRepository.findById(eventDTO.venueId());
            if (!existingVenue.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
            } else {
                Event editedEvent = existingEvent.get();
                editedEvent.setName(eventDTO.name());
                editedEvent.setDescription(eventDTO.description());
                editedEvent.setTotal_tickets(eventDTO.total_tickets());
                editedEvent.setBegins_at(eventDTO.begins_at());
                editedEvent.setEnds_at(eventDTO.ends_at());
                editedEvent.setTicket_sale_begins(eventDTO.ticket_sale_begins());
                editedEvent.setVenue(existingVenue.get());

                Event savedEvent = eventRepository.save(editedEvent);

                EventDTO responseDTO = new EventDTO(
                        savedEvent.getId(),
                        savedEvent.getName(),
                        savedEvent.getDescription(),
                        savedEvent.getTotal_tickets(),
                        savedEvent.getBegins_at(),
                        savedEvent.getEnds_at(),
                        savedEvent.getTicket_sale_begins(),
                        savedEvent.getVenue().getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            }
        }
    }

    // Delete event with DELETE Request
    @DeleteMapping("/{id}")
    public Iterable<Event> deleteEvent(@PathVariable("id") Long eventId) {
        // Finds the event with the mapped id from the repository; assings null if not
        // found
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        // Checks if the event is null or not null
        if (!optionalEvent.isPresent()) {
            // If null (ie. not found), throws exception and error message
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Event not found");
        }
        // If not null (ie. found), deletes the event with the mapped id from the
        // repository
        eventRepository.deleteById(eventId);
        // Returns all the remaining events (without the removed event)
        return eventRepository.findAll();
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

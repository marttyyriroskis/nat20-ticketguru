package com.nat20.ticketguru.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }

    // Get event by id
    @GetMapping("/{id}")
    public Event getEvent(@PathVariable("id") Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Event not found");
        }
        Event event = optionalEvent.get();
        return event;
    }

    // Post a new event
    @PostMapping("")
    public Event createEvent(@Valid @RequestBody Event event) {

        // checks if the venue id is null instead of entire venue being null
        if (event.getVenue() != null && event.getVenue().getId() == null) {
            event.setVenue(null);
        }
        // if venue is not null, check if it is already exists
        if (event.getVenue() != null) {
            Optional<Venue> existingVenue = venueRepository.findById(event.getVenue().getId());

            if (!existingVenue.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Venue does not exist!");
            }

            event.setVenue(existingVenue.get());

        }
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    // Edit event with PUT request
    @PutMapping("/{id}")
    public Event editEvent(@Valid @RequestBody Event editedEvent, @PathVariable("id") Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Event not found");
        }
        Event event = optionalEvent.get();

        // update fields from editedEvent in a way that respects field validation rules
        event.setName(editedEvent.getName());
        event.setDescription(editedEvent.getDescription());
        event.setTotal_tickets(editedEvent.getTotal_tickets());
        event.setBegins_at(editedEvent.getBegins_at());
        event.setEnds_at(editedEvent.getEnds_at());
        event.setTicket_sale_begins(editedEvent.getTicket_sale_begins());

        // no changes to Venue fields allowed here: can set venue to null or to a
        // different existing venue
        Venue editedVenue = editedEvent.getVenue();
        if (editedVenue != null) {
            Optional<Venue> existingVenue = venueRepository.findById(editedVenue.getId());
            if (!existingVenue.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid venue");
            }
            event.setVenue(existingVenue.get());
        } else {
            event.setVenue(null);
        }

        return eventRepository.save(event);
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

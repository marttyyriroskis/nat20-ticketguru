package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.VenueRepository;

@RestController
@RequestMapping("/api")
public class RestEventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    VenueRepository venueRepository;

    // Get events
    @GetMapping("/events")
    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }

    // Get event by id
    @GetMapping("/event/{id}")
    <Optional> Event getEvent(@PathVariable("id") Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    // Post a new event
    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    // Edit event with PUT request
    @PutMapping("events/{id}")
    public Event editEvent(@RequestBody Event editedEvent, @PathVariable("id") Long eventId) {
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

        // no changes to Venue fields allowed here: can set venue to null or to a different existing venue
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
}

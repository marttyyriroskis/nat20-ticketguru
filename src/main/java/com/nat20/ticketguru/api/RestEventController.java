package com.nat20.ticketguru.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.repository.EventRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class RestEventController {

    @Autowired
    EventRepository eventRepository;

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

    @PutMapping("events/{id}")
    public Event editEvent(@RequestBody Event editedEvent, @PathVariable("id") Long eventId) {
        editedEvent.setId(eventId);
        return eventRepository.save(editedEvent);
    }
}

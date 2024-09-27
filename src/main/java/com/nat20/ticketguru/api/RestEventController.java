package com.nat20.ticketguru.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.repository.EventRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PathVariable;

import com.nat20.ticketguru.domain.Event;
>>>>>>> GetEvent


@RestController
@RequestMapping("/api")
public class RestEventController {

    @Autowired
    EventRepository eventRepository;

<<<<<<< HEAD
    // Get events
    @GetMapping("/events")
    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }
=======
    @GetMapping("/event/{id}")
    <Optional>Event getEvent(@PathVariable("id") Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }
    
>>>>>>> GetEvent

}

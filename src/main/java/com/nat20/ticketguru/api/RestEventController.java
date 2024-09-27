package com.nat20.ticketguru.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.nat20.ticketguru.repository.EventRepository;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class RestEventController {
    @Autowired
    EventRepository eventRepository;

}

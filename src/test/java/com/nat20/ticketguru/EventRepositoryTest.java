package com.nat20.ticketguru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.repository.ZipcodeRepository;
import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.domain.Zipcode;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ZipcodeRepository zipcodeRepository;

    @Test
    public void testAddEvent() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        venue.setDeletedAt(null);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2024, 11, 14, 20, 0),
            LocalDateTime.of(2024, 11, 14, 23, 0),
            LocalDateTime.of(2024, 10, 1, 10, 0),
            savedVenue,
            null
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        assertNotNull(savedEvent.getId(), "Event id should not be null");
        assertEquals("Concert", savedEvent.getName(), "Event name should match");
        assertEquals("An amazing live concert", savedEvent.getDescription(), "Event description should match");
        assertEquals(200, savedEvent.getTotalTickets(), "Total tickets should match");
        assertEquals(LocalDateTime.of(2024, 11, 14, 20, 0), savedEvent.getBeginsAt(), "Event start time should match");
        assertEquals(LocalDateTime.of(2024, 11, 14, 23, 0), savedEvent.getEndsAt(), "Event end time should match");
        assertEquals(LocalDateTime.of(2024, 10, 1, 10, 0), savedEvent.getTicketSaleBegins(), "Ticket sale begin time should match");
        assertEquals(savedVenue.getId(), savedEvent.getVenue().getId(), "Event venue ID should match saved venue");
    }

    @Test
    public void testDeleteEvent() {
         // Arrange
         Zipcode zipcode = new Zipcode();
         zipcode.setCity("Vantaa");
         zipcode.setZipcode("01000");
         Zipcode savedZipcode = zipcodeRepository.save(zipcode);
 
         Venue venue = new Venue(); 
         venue.setId(1L);
         venue.setName("Kaatoluola");
         venue.setAddress("mikonkatu 1");
         venue.setZipcode(savedZipcode);
         venue.setDeletedAt(null);
         Venue savedVenue = venueRepository.save(venue); 
 
         Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2024, 11, 14, 20, 0),
            LocalDateTime.of(2024, 11, 14, 23, 0),
            LocalDateTime.of(2024, 10, 1, 10, 0),
            savedVenue,
            null
        );

        Event savedEvent = eventRepository.save(event);
        Long eventId = savedEvent.getId();

        // Act
        eventRepository.deleteById(eventId);

        // Assert
        Optional<Event> deletedEvent = eventRepository.findById(eventId);
        assertTrue(deletedEvent.isEmpty());
    }
    
}

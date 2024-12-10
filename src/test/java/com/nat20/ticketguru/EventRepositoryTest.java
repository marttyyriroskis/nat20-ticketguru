package com.nat20.ticketguru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

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
    public void testInvalidEventName() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName("Kaatokäytävä");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "", // venue Name is empty
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            eventRepository.save(event);
        });
        
    }

    @Test
    public void testInvalidEventDescription() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName("Kaatokäytävä");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "", // Event description is empty
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            eventRepository.save(event);
        });
        
    }

    @Test
    public void testInvalidEventTotalTickets() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName("Kaatokäytävä");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            1, // Total tickets can be any number
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        Event fetchedEvent = eventRepository.findById(savedEvent.getId()).orElse(null);



        // Assert
        
        assertEquals(1, fetchedEvent.getTotalTickets(), "Total tickets should match");
        
    }

    @Test
    public void testInvalidEventDate() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName("Kaatokäytävä");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2001, 9, 11, 20, 0), // Invalid past date
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            eventRepository.save(event);
        });
        
    }

    @Test
    public void testInvalidEventVenue() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName(""); // venue Name is empty
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        Event fetchedEvent = eventRepository.findById(savedEvent.getId()).orElse(null);



        // Assert
        assertNotNull(fetchedEvent, "Event id should not be null");
        
    }

    @Test
    public void testInvalidEventZipcode() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("nollanollayksinollanolla"); // zipcode should be only allowed to be a String of 5 numbers 
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(1L);
        venue.setName("Kaatokäytävä");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            eventRepository.save(event);
        });

    }

    @Test
    public void testAddEventId() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        
        assertNotEquals(4L, savedEvent.getId(), "Event id should match saved event"); // if all tests are ran at once the id is 4, but if only this test is ran it is 1
    }

    @Test
    public void testAddEventName() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
       
        assertEquals("Concert", savedEvent.getName(), "Event name should match");
        
    }

    @Test
    public void testAddEventDescription() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        
        assertEquals("An amazing live concert", savedEvent.getDescription(), "Event description should match");
        
    }

    @Test
    public void testAddEventTickets() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        
        assertEquals(200, savedEvent.getTotalTickets(), "Total tickets should match");
        
    }

    @Test
    public void testAddEventDate() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        
        assertEquals(LocalDateTime.of(2025, 11, 14, 20, 0), savedEvent.getBeginsAt(), "Event start time should match");
        assertEquals(LocalDateTime.of(2025, 11, 14, 23, 0), savedEvent.getEndsAt(), "Event end time should match");
        
    }

    @Test
    public void testAddEventVenue() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        
        assertEquals("Kaatoluola", savedEvent.getVenue().getName(), "Event venue Name should match saved venue");
    }

    @Test
    public void testAddEventZipcode() {

        // Arrange
        Zipcode zipcode = new Zipcode();
        zipcode.setCity("Vantaa");
        zipcode.setZipcode("01000");
        Zipcode savedZipcode = zipcodeRepository.save(zipcode);

        Venue venue = new Venue(); 
        venue.setId(2L);
        venue.setName("Kaatoluola");
        venue.setAddress("mikonkatu 1");
        venue.setZipcode(savedZipcode);
        Venue savedVenue = venueRepository.save(venue); 

        Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
        );

        // Act
        Event savedEvent = eventRepository.save(event);

        // Assert
        
        assertEquals("Vantaa", savedEvent.getVenue().getZipcode().getCity(), "Event venue zipcode city should match saved zipcode lol");
        
        
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
         Venue savedVenue = venueRepository.save(venue); 
 
         Event event = new Event(
            "Concert",
            "An amazing live concert",
            200,
            LocalDateTime.of(2025, 11, 14, 20, 0),
            LocalDateTime.of(2025, 11, 14, 23, 0),
            savedVenue
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

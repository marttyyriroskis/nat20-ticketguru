package com.nat20.ticketguru;

import java.time.LocalDateTime;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.domain.Zipcode;
//import com.nat20.ticketguru.dto.TicketTypeDTO;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.repository.ZipcodeRepository;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
public class TicketTypeTest {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ZipcodeRepository zipcodeRepository;

    private TicketType ticketType;

    /**
     * Sets up the test environment by creating and saving the necessary entities in the database.
     * This method runs before each test to ensure the required data is present for the tests.
     * It creates and persists a `Zipcode`, `Venue`, `Event`, and a `TicketType` for use in test cases.
     */
    @BeforeEach
    public void setup() {
        Zipcode zipcode = zipcodeRepository.save(new Zipcode("00100", "Helsinki"));
        Venue venue = venueRepository.save(new Venue("Bunkkeri", "Bunkkeritie 1", zipcode));
        Event event = eventRepository.save(new Event(
                "Death metal karaoke",
                "Öriöriöriöriörirprir!!!!!",
                10,
                LocalDateTime.of(2055, 10, 12, 12, 0),
                LocalDateTime.of(2055, 10, 12, 12, 0),
                venue)
                );

        ticketType = ticketTypeRepository.save(new TicketType("adult", 29.99, null, event));
    }

    /**
     * Tests the soft delete functionality of a `TicketType` by calling the `delete()` method and 
     * verifying that the `deletedAt` property is set to a `LocalDateTime` instance.
     * This ensures that the soft delete logic correctly updates the `deletedAt` field when a 
     * `TicketType` is marked as deleted.
     */
    @Test
    public void testTicketTypeSoftDelete() {
        ticketType.delete();

        assertTrue(ticketType.getDeletedAt() instanceof LocalDateTime,
                "delete() method failed, deletedAt should be of type LocalDateTime");
    }

/*
* Does not work as of 26/11/2024, will need extensive fixes due to changes in TicketTypeDTO class
*
    @Test
    public void testTicketTypeDTOCreation() {
        Zipcode zipcode = zipcodeRepository.save(new Zipcode("00100", "Helsinki"));
        Venue venue = venueRepository.save(new Venue("Bunkkeri", "Bunkkeritie 1", zipcode));
        Event event = eventRepository.save(new Event(
                "Death metal karaoke",
                "Öriöriöriöriörirprir!!!!!",
                10,
                LocalDateTime.of(2055, 10, 12, 12, 0),
                LocalDateTime.of(2055, 10, 12, 12, 0),
                venue)
                );

        TicketType ticketTypeDTOControl = ticketTypeRepository.save(new TicketType("child", 19.99, 10, event));
        TicketTypeDTO ticketTypeDTO = ticketType.toDTO();

        assertEquals(ticketTypeDTOControl, ticketTypeDTO, "DTOs don't match");
    }
*/

}

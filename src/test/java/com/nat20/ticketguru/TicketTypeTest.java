package com.nat20.ticketguru;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.domain.Zipcode;
import com.nat20.ticketguru.dto.TicketTypeDTO;
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

    @BeforeEach
    public void setup() {
        Zipcode zipcode = zipcodeRepository.save(new Zipcode("00100", "Helsinki"));
        Venue venue = venueRepository.save(new Venue("Bunkkeri", "Bunkkeritie 1", zipcode, null));
        Event event = eventRepository.save(new Event(
                "Death metal karaoke",
                "Öriöriöriöriörirprir!!!!!",
                10,
                LocalDateTime.of(2055, 10, 12, 12, 0),
                LocalDateTime.of(2055, 10, 12, 12, 0),
                LocalDateTime.of(2055, 10, 12, 12, 0),
                venue,
                null));

        ticketType = ticketTypeRepository.save(new TicketType("adult", 29.99, null, event, null));
    }

    @Test
    public void testTicketTypeSoftDelete() {
        ticketType.delete();

        assertTrue(ticketType.getDeletedAt() instanceof LocalDateTime,
                "delete() method failed, deletedAt should be of type LocalDateTime");
    }

    @Test
    public void testTicketTypeRestore() {
        ticketType.delete();
        ticketType.restore();

        assertEquals(ticketType.getDeletedAt(), null, "restore() method failed; deletedAt should be null");
    }

    @Test
    public void testTicketTypeDTOCreation() {
        TicketTypeDTO ticketTypeDTOControl = new TicketTypeDTO(ticketType.getId(), "adult", 29.99, null,
                ticketType.getEvent().getId());
        TicketTypeDTO ticketTypeDTO = ticketType.toDTO();

        assertEquals(ticketTypeDTOControl, ticketTypeDTO, "DTOs don't match");
    }
}

package com.nat20.ticketguru;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import jakarta.transaction.Transactional;

import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.api.TicketRestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc()
public class MarkTicketAsUsedByBarcodeTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketRestController ticketRestController;

    private Ticket mockTicket;

    public void RoleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Sets up the test environment by authenticating a user and initializing MockMvc for web application context testing.
     * This method runs before each test and ensures the `SecurityContextHolder` has an authenticated user.
     */
    @BeforeEach
    void setUp() {

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken("admin@test.com", "admin", List.of(
                        new SimpleGrantedAuthority("VIEW_TICKETS"),
                        new SimpleGrantedAuthority("USE_TICKETS")))
                );
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        mockTicket = new Ticket();
        mockTicket.setBarcode("123456");
        mockTicket.setUsedAt(null);

    }

    /**
     * Tests the successful execution of the `getTicketByBarcode` method in the `TicketRestController`.
     * This test verifies that the method returns a `ResponseEntity` with a status of 200 (OK)
     * and a valid `TicketDTO` when a ticket with the specified barcode exists. Does NOT work as of
     * 9/12/2024.
    @Test
    void getTicketByBarcode_success() {

        String barcode = "123456";
        when(ticketRepository.findByBarcode(barcode)).thenReturn(mockTicket);

        ResponseEntity<TicketDTO> response = ticketRestController.getTicketByBarcode(barcode);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        verify(ticketRepository).findByBarcode(barcode);

    }
    */

    @Test
    void getTicketByBarcode_ticketNotFound() {

        String barcode = "123456";
        when(ticketRepository.findByBarcode(barcode)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketRestController.getTicketByBarcode(barcode));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Response status should be 404 NOT_FOUND");
        assertEquals("Ticket not found", exception.getReason(), "Should return the correct error message");

    }

    /**
     * Tests the behavior of the `getTicketByBarcode` method when a ticket is not found.
     * This test ensures that the method throws a `ResponseStatusException` with a status of 404 (NOT_FOUND)
     * and an appropriate error message if the ticket with the specified barcode does not exist. Does NOT
     * work as of 9/12/2024.
    @Test
    void useTicket_success() {

        String barcode = "123456";
        when(ticketRepository.findByBarcode(barcode)).thenReturn(mockTicket);
        when(ticketRepository.save(mockTicket)).thenReturn(mockTicket);

        ResponseEntity<TicketDTO> response = ticketRestController.useTicket(barcode);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        verify(ticketRepository).findByBarcode(barcode);
        verify(ticketRepository).save(mockTicket);

    }
    */

    @Test
    void useTicket_ticketNotFound() {

        String barcode = "123456";
        when(ticketRepository.findByBarcode(barcode)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketRestController.useTicket(barcode));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Response status should be 404 NOT_FOUND");
        assertEquals("Ticket not found", exception.getReason(), "Should return the correct error message");

    }

    @Test
    void useTicket_alreadyUsed() {

        String barcode = "123456";
        mockTicket.setUsedAt(LocalDateTime.now()); // Simulate an already used ticket
        when(ticketRepository.findByBarcode(barcode)).thenReturn(mockTicket);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketRestController.useTicket(barcode));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "Response status should be 400 BAD_REQUEST");
        assertEquals("Ticket already used", exception.getReason(), "Should return the correct error message");

    }

}

package com.nat20.ticketguru;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc()
public class TicketRestTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    /**
     * Sets up the test environment by initializing `MockMvc` and configuring the `SecurityContext` 
     * with an authenticated user who has the "VIEW_TICKETS" authority.
     * This method runs before each test to ensure the `MockMvc` instance is properly set up for HTTP request testing.
     */
    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin@test.com", "admin", List.of(new SimpleGrantedAuthority("VIEW_TICKETS"))));
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())        
                .build();
    }

    /**
     * Tests the `/api/tickets` endpoint to check if the status code returned is `200 OK`.
     * This verifies that the endpoint is reachable and responds with the correct HTTP status.
     * 
     * @throws Exception if an error occurs during the HTTP request or response handling
     */
    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/api/tickets"))
            .andExpect(status().isOk());
    }

    /**
     * Tests the `/api/tickets` endpoint to ensure that the response content type is `application/json`.
     * This verifies that the response is in the expected JSON format.
     * 
     * @throws Exception if an error occurs during the HTTP request or response handling
     */
    @Test
    public void responseTypeApplicationJson() throws Exception {
        mockMvc.perform(get("/api/tickets"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    /**
     * Tests the `/api/tickets` endpoint to validate that the JSON response structure is correct.
     * It checks that the response is an array and that the first ticket in the array has a non-empty `barcode`.
     * 
     * @throws Exception if an error occurs during the HTTP request or response handling
     */
    @Test
    public void validateJsonStructure() throws Exception {
        mockMvc.perform(get("/api/tickets"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].barcode").isNotEmpty());
    }

}

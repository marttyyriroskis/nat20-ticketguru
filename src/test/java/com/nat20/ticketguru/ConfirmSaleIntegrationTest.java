package com.nat20.ticketguru;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nat20.ticketguru.service.TicketSummaryService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class ConfirmSaleIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketSummaryService ticketSummaryService;

    /**
     * Sets up the test environment by initializing `MockMvc` and configuring a mock response for the `ticketSummaryService`.
     * This method runs before each test and ensures that the `MockMvc` instance is properly set up for HTTP request testing.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        Mockito.when(ticketSummaryService.countAvailableTicketsForTicketType(Mockito.any()))
                .thenReturn(10); // Simulate 10 tickets available
    }

    /**
     * Tests the behavior of the `/api/sales/confirm` endpoint with a valid `BasketDTO` payload.
     * This test verifies that the endpoint returns a `201 Created` status when a valid basket is submitted.
     * 
     * @throws Exception if an error occurs during the HTTP request or response handling
     */
    @Test
    public void testConfirmValidBasketDTO() throws Exception {
        String validBasketPayload = """
                {
                    "ticketItems": [
                        {
                        "ticketTypeId": 1,
                        "quantity": 3,
                        "price": 29.99
                        }
                        ]
                    }
                }
                """;

        mockMvc.perform(post("/api/sales/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(httpBasic("admin@test.com", "admin"))
                .content(validBasketPayload))
                .andExpect(status().isCreated());

    }

    /**
     * Tests the behavior of the `/api/sales/confirm` endpoint with an invalid `BasketDTO` payload.
     * This test verifies that the endpoint returns a `400 Bad Request` status when a payload that lacks required fields is submitted.
     * 
     * @throws Exception if an error occurs during the HTTP request or response handling
     */
    @Test
    public void testConfirmInvalidBasketDTO() throws Exception {
        String invalidBasketPayload = """
                {
                    "ticketItems": [
                        {

                        "quantity": 3,
                        "price": 29.99
                        }
                        ]
                    }
                }
                """;

        mockMvc.perform(post("/api/sales/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(httpBasic("admin@test.com", "admin"))
                .content(invalidBasketPayload))
                .andExpect(status().isBadRequest());
    }

}

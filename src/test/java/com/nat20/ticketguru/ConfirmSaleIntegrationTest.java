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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        Mockito.when(ticketSummaryService.countAvailableTicketsForTicketType(Mockito.any()))
                .thenReturn(10); // Simulate 10 tickets available
    }

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

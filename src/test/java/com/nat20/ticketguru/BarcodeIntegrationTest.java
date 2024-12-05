package com.nat20.ticketguru;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc()
public class BarcodeIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Sets up the test environment by authenticating a user and initializing MockMvc for web application context testing.
     * This method runs before each test and ensures the `SecurityContextHolder` has an authenticated user.
     */
    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin@test.com", "admin", List.of(new SimpleGrantedAuthority("CREATE_TICKETS"))));
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Tests that posting two tickets with the same details results in tickets with different barcodes.
     * This test ensures that the barcode generation logic is functioning correctly and that duplicate barcodes are not allowed.
     * 
     * @throws Exception if an error occurs during the HTTP request or response handling
     */
    @Test
    public void testDuplicateBarcodeNotAllowed() throws Exception {
        String ticketPayload = """
            {
                "usedAt":null,
                "price":29.99,
                "ticketTypeId":1,
                "saleId":1
            }
        """;

        MvcResult result1 = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(ticketPayload))
                .andExpect(status().isCreated())
                .andReturn();


        String barcode1 = extractBarcodeFromResponse(result1);
        MvcResult result2 = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(ticketPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String barcode2 = extractBarcodeFromResponse(result2);
        assertNotEquals(barcode1, barcode2);
    }

    /**
     * Extracts the barcode from the response JSON of an HTTP request.
     * 
     * @param result the MvcResult object containing the HTTP response
     * @return the extracted barcode from the response
     * @throws Exception if an error occurs while parsing the response JSON
     */
    private String extractBarcodeFromResponse(MvcResult result) throws Exception {
        String jsonResponse = result.getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(jsonResponse, "$.barcode");
    }

}

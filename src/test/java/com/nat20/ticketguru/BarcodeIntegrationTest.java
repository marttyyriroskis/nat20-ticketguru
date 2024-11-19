package com.nat20.ticketguru;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

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
@SpringBootTest(properties = "spring.profiles.active=test", webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
public class BarcodeIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin@test.com", "admin", List.of(new SimpleGrantedAuthority("CREATE_TICKETS"))));
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    // Post two duplicate Tickets, then check their barcodes are dissimilar
    @Test
    @WithMockUser(username = "admin@test.com", password = "admin", roles = {"ADMIN"})
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

    private String extractBarcodeFromResponse(MvcResult result) throws Exception {
        String jsonResponse = result.getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(jsonResponse, "$.barcode");
    }

}

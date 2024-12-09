package com.nat20.ticketguru;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.nat20.ticketguru.api.SaleRestController;

import com.nat20.ticketguru.service.SaleService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc()
public class SearchSaleTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleRestController saleRestController;

    public void SaleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Sets up the test environment by authenticating a user and initializing MockMvc for web application context testing.
     * This method runs before each test and ensures the `SecurityContextHolder` has an authenticated user.
     */
    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken("admin@test.com", "admin", List.of(
                       new SimpleGrantedAuthority("VIEW_SALES")))
                );
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Tests the successful execution of the `searchSales` method in the `SaleRestController`.
     * 
     * This test verifies that the `searchSales` method returns a `ResponseEntity` with a status
     * of 200 (OK) and a list of `SaleDTO` objects that matches the number of sales returned
     * by the `SaleService`. Does NOT work as of 9/12/2024.
    @Test
    void searchSales_success() {
        
        String start = "2023-12-01T00:00";
        String end = "2025-12-07T23:59";
        Long userId = 1L;

        List<Sale> sales = List.of(new Sale());
        when(saleService.searchSales(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end),
                userId
        )).thenReturn(sales);

        ResponseEntity<List<SaleDTO>> response = saleRestController.searchSales(start, end, userId);

        assertEquals(200, response.getStatusCode(), "Response status should be 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(sales.size(), response.getBody().size(), "Response should contain the same number of sales as returned by the service");
    
    }
    */

    /**
     * Tests the `searchSales` method when no parameters are provided.
     * This test ensures that the method throws a `ResponseStatusException`
     * with a 400 BAD_REQUEST status and the correct error message when
     * no search parameters are given.
     */
    @Test
    void searchSales_missingParameters() {

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saleRestController.searchSales(null, null, null));

        assertEquals(400, exception.getStatusCode().value(), "Response status should be 400 BAD_REQUEST");
        assertEquals("At least one search parameter must be provided.", exception.getReason(), "Should return the correct error message");
    
    }

    /**
     * Tests the `searchSales` method when the service throws an exception.
     * This test simulates a scenario where `saleService.searchSales` throws
     * a `RuntimeException` and ensures that the `searchSales` method handles it properly.
     * The test verifies that a `ResponseStatusException` with a 404 NOT_FOUND status
     * and the exception's message as the reason is thrown.
     */
    @Test
    void searchSales_serviceThrowsException() {

        String start = "2024-12-01T00:00";
        String end = "2024-12-07T23:59";
        Long userId = 1L;

        when(saleService.searchSales(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end),
                userId
        )).thenThrow(new RuntimeException("Test exception"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saleRestController.searchSales(start, end, userId));

        assertEquals(404, exception.getStatusCode().value(), "Response status should be 404 NOT_FOUND");
        assertEquals("Test exception", exception.getReason(), "Should return the correct error message from the exception");

    }

}

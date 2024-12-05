package com.nat20.ticketguru;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.domain.User;

import jakarta.transaction.Transactional;


@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc()
public class UserRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test to verify that an admin user is able to create a new user through the /api/users endpoint.
     * This test sets up an admin user, mocks the authentication context, and sends a POST request
     * with the new user's details. It expects a 201 Created status if the request is successful.
     */
    @Test
    void adminUserCanCreateNewUser() throws Exception {
        User adminUser = new User("admin@test.com", "Admin", "Admin", "hashedPassword");
        Role adminRole = new Role("ADMIN");
        adminRole.addPermissions(Permission.values());
        adminUser.setRole(adminRole);

        // Mock authentication context
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                adminUser,
                null,
                adminUser.getAuthorities()
            )
        );

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "newtestuser1@test.com",
                            "firstName": "New1",
                            "lastName": "User1",
                            "password": "newtestuserpassword1",
                            "roleId": 2
                        }
                        """))
                .andExpect(status().isCreated());
    }

    /**
     * Test to verify that a non-admin user (e.g., a salesperson) cannot create a new user through the /api/users endpoint.
     * This test sets up a salesperson user, mocks the authentication context, and sends a POST request
     * with the new user's details. It expects a 403 Forbidden status if the request is denied due to insufficient permissions.
     */
    @Test
    void nonAdminUserCannotCreateNewUser() throws Exception {
        User salespersonUser = new User("salesperson@test.com", "Salesperson", "User", "hashedPassword");
        Role salespersonRole = new Role("SALESPERSON");
        salespersonRole.addPermissions(Permission.VIEW_SALES, Permission.CREATE_SALES); // Example permissions
        salespersonUser.setRole(salespersonRole);

        // Mock authentication context
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                salespersonUser,
                null,
                salespersonUser.getAuthorities()
            )
        );

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "newtestuser2@test.com",
                            "firstName": "Newer",
                            "lastName": "User",
                            "password": "newtestuserpassword",
                            "roleId": 2
                        }
                        """))
                .andExpect(status().isForbidden());
    }

}

package com.nat20.ticketguru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.nat20.ticketguru.dto.PermissionDTO;
import com.nat20.ticketguru.dto.RoleDTO;
import com.nat20.ticketguru.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

import com.nat20.ticketguru.api.RoleRestController;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.domain.Permission;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc()
public class AddAndRemovePermissionToRoleTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleRestController roleRestController;

    public void RoleControllerTest() {
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
                        new SimpleGrantedAuthority("GRANT_PERMISSIONS"),
                        new SimpleGrantedAuthority("REVOKE_PERMISSIONS")))
                );
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Tests the successful addition of a permission to a role.
     * Verifies that the role is updated with the new permission and saved in the repository.
     */
    @Test
    void addPermissionToRole_success() {

        Long roleId = 1L;
        PermissionDTO permissionRequest = new PermissionDTO(Permission.VIEW_EVENTS);
        Role role = new Role("TEST");
        when(roleRepository.findByIdActive(roleId)).thenReturn(Optional.of(role));

        ResponseEntity<RoleDTO> response = roleRestController.addPermissionToRole(roleId, permissionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(roleRepository).save(role);
        assertEquals(role.toDTO(), response.getBody());
    }

    /**
     * Tests the scenario where a permission is already assigned to a role.
     * Verifies that a {@link ResponseStatusException} with {@code HttpStatus.CONFLICT} is thrown.
     */
    @Test
    void addPermissionToRole_permissionAlreadyExists() {
        
        Long roleId = 1L;
        Permission permission = Permission.VIEW_EVENTS;
        PermissionDTO permissionRequest = new PermissionDTO(permission);
        Role role = new Role("TEST");
        role.addPermissions(permission);
        when(roleRepository.findByIdActive(roleId)).thenReturn(Optional.of(role));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> roleRestController.addPermissionToRole(roleId, permissionRequest));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Role already has this permission", exception.getReason());
    }

    /**
     * Tests the successful removal of a permission from a role.
     * Verifies that the permission is removed, the role is updated and saved in the repository,
     * and the response contains the updated role.
     */
    @Test
    void removePermissionFromRole_success() {
        Long roleId = 1L;
        Permission permissionToRemove = Permission.VIEW_EVENTS;
        Role role = new Role("TEST");
        role.addPermissions(permissionToRemove);
        when(roleRepository.findByIdActive(roleId)).thenReturn(Optional.of(role));

        ResponseEntity<RoleDTO> response = roleRestController.removePermissionFromRole(roleId, permissionToRemove);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(roleRepository).save(role);
        assertEquals(role.toDTO(), response.getBody());
    }

    /**
     * Tests the scenario where a permission to be removed is not assigned to the role.
     * Verifies that a {@link ResponseStatusException} with {@code HttpStatus.NOT_FOUND} is thrown
     * and contains the appropriate error message.
     */
    @Test
    void removePermissionFromRole_permissionNotAssigned() {
        Long roleId = 1L;
        Permission permissionToRemove = Permission.VIEW_EVENTS;
        Role role = new Role("TEST");
        when(roleRepository.findByIdActive(roleId)).thenReturn(Optional.of(role));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> roleRestController.removePermissionFromRole(roleId, permissionToRemove));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Role does not have this permission", exception.getReason());
    }

}

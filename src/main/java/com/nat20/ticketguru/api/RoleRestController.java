package com.nat20.ticketguru.api;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.dto.PermissionDTO;
import com.nat20.ticketguru.dto.RoleDTO;
import com.nat20.ticketguru.repository.RoleRepository;

import jakarta.validation.Valid;

/**
 * REST controller for roles
 *
 * @author Jesse Hellman
 * @version 1.0
 */
@RestController
@RequestMapping("/api/roles")
@Validated
public class RoleRestController {

    private final RoleRepository roleRepository;

    public RoleRestController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Get all roles
     *
     * @return all roles
     * @throws ResponseStatusException if there are no roles
     */
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getRoles() {
        Iterable<Role> iterableRoles = roleRepository.findAllActive();

        if (!iterableRoles.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No roles available");
        }

        List<Role> roleList = new ArrayList<>();
        iterableRoles.forEach(roleList::add);

        return ResponseEntity.ok(roleList.stream()
                .map(Role::toDTO)
                .collect(Collectors.toList()));
    }

    /**
     * Get a role by id
     *
     * @param id the id of the role
     * @return the role
     * @throws ResponseStatusException if the role is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        Role role = roleRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        return ResponseEntity.ok(role.toDTO());
    }

    /**
     * Add a role
     *
     * @param roleDTO the role to add
     * @return the added role
     * @throws ResponseStatusException if the role already exists
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoleDTO> addRole(@Valid @RequestBody RoleDTO roleDTO) {

        if (roleRepository.findByTitle(roleDTO.title()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists");
        }

        Role role = new Role();
        role.setTitle(roleDTO.title());

        Role newRole = roleRepository.save(role);

        return ResponseEntity.status(HttpStatus.CREATED).body(newRole.toDTO());
    }

    /**
     * Update a role
     *
     * @param id the id of the role to be updated
     * @param roleDTO the updated role
     * @return the updated role
     * @throws ResponseStatusException if the role is not found
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {

        Role existingRole = roleRepository.findByIdActive(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        Role titledRole = roleRepository.findByTitle(roleDTO.title()).orElse(null);

        if (titledRole != null && titledRole.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists");
        }

        existingRole.setTitle(roleDTO.title());

        Role updatedRole = roleRepository.save(existingRole);

        return ResponseEntity.ok(updatedRole.toDTO());
    }

    /**
     * Delete a role
     *
     * @param id the id of the role to delete
     * @return no content
     * @throws ResponseStatusException if the role is not found
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Role role = roleRepository.findByIdActive(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        role.delete(); // Mark as deleted (soft delete)
        roleRepository.save(role);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Add a permission to a role
     *
     * @param id
     * @param permissionRequest the permission to add
     * @return the role with the added permission
     * @throws ResponseStatusException if the role or permission is not found,
     * or if the role already has the permission
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/permissions")
    public ResponseEntity<RoleDTO> addPermissionToRole(@PathVariable Long id, @RequestBody PermissionDTO permissionRequest) {

        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }

        if (!EnumSet.allOf(Permission.class).contains(permissionRequest.permission())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }

        if (optionalRole.get().hasPermission(permissionRequest.permission())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Role already has this permission");
        }

        Role role = optionalRole.get();
        role.addPermission(permissionRequest.permission());
        roleRepository.save(role);

        return ResponseEntity.ok(role.toDTO());
    }

    /**
     * Remove a permission from a role
     *
     * @param id
     * @param permissionToRemove the permission to be removed
     * @return the role with the removed permission
     * @throws ResponseStatusException if the role or permission is not found,
     * or if the role does not have the permission
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/permissions/{permissionId}")
    public ResponseEntity<RoleDTO> removePermissionFromRole(@PathVariable Long id, @RequestParam("permission") Permission permissionToRemove) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }

        if (!EnumSet.allOf(Permission.class).contains(permissionToRemove)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }

        if (!optionalRole.get().hasPermission(permissionToRemove)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role does not have this permission");
        }
        Role role = optionalRole.get();
        role.removePermission(permissionToRemove);

        roleRepository.save(role);

        return ResponseEntity.ok(role.toDTO());
    }

}

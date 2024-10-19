package com.nat20.ticketguru.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.dto.RoleDTO;
import com.nat20.ticketguru.repository.RoleRepository;

import jakarta.validation.Valid;

import com.nat20.ticketguru.repository.PermissionRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * REST controller for roles
 * 
 * @author Jesse Hellman
 * @version 1.0
 */
@RestController
@RequestMapping("/api/roles")
public class RestRoleController {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RestRoleController(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
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
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No roles found");
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
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {

        Role existingRole = roleRepository.findByIdActive(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

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
     * @throws ResponseStatusException if the role or permission is not found, or if the role already has the permission
     */
    @PostMapping("/{id}/permissions")
    public ResponseEntity<Role> addPermissionToRole(@PathVariable Long id, @RequestBody Permission permissionRequest) {
        Long permissionId = permissionRequest.getId();
        
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }

        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (!optionalPermission.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }

        if (optionalRole.get().hasPermission(optionalPermission.get())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Role already has this permission");
        }
        
        Role role = optionalRole.get();
        Permission permission = optionalPermission.get();

        role.addPermission(permission);

        roleRepository.save(role);

        return ResponseEntity.ok(role);
    }

    /**
     * Remove a permission from a role
     * 
     * @param id
     * @param permissionId the id of the permission to be removed
     * @return the role with the removed permission
     * @throws ResponseStatusException if the role or permission is not found, or if the role does not have the permission
     */
    @DeleteMapping("/{id}/permissions/{permissionId}")
    public ResponseEntity<Role> removePermissionFromRole(@PathVariable Long id, @PathVariable Long permissionId) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }

        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (!optionalPermission.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }

        Role role = optionalRole.get();
        Permission permission = optionalPermission.get();

        if (!role.hasPermission(permission)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role does not have this permission");
        }

        role.removePermission(permission);

        roleRepository.save(role);

        return ResponseEntity.ok(role);
    }

    
}

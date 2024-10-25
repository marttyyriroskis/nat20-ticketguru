package com.nat20.ticketguru.api;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.dto.PermissionDTO;
import com.nat20.ticketguru.repository.PermissionRepository;

import jakarta.validation.Valid;

/**
 * REST controller for permissions
 * 
 * @author Jesse Hellman
 * @version 1.0
 */
@RestController
@RequestMapping("/api/permissions")
@Validated
public class PermissionRestController {
    private final PermissionRepository permissionRepository;

    public PermissionRestController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Get all permissions
     * 
     * @return all permissions
     * @throws ResponseStatusException if there are no permissions
     */
    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getPermissions() {
        List<Permission> permissions = permissionRepository.findAllActive();

        if (permissions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No permissions found");
        }

        List<PermissionDTO> permissionDTOs = permissions.stream()
            .map(Permission::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(permissionDTOs);
    }

    /**
     * Get a permission by id
     * 
     * @param id the id of the permission
     * @return the permission
     * @throws ResponseStatusException if the permission is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable Long id) {
        Permission permission = permissionRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found"));

        return ResponseEntity.ok(permission.toDTO());
    }

    /**
     * Add a new permission
     * 
     * @param permissionDTO the permission to add
     * @return the added permission
     * @throws ResponseStatusException if the permission title already exists
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PermissionDTO> addPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        if (permissionRepository.findByTitle(permissionDTO.title()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Permission already exists");
        }

        Permission permission = new Permission();
        permission.setTitle(permissionDTO.title());

        Permission newPermission = permissionRepository.save(permission);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPermission.toDTO());
    }

    /**
     * Update a permission
     * 
     * @param id the id of the permission to be updated
     * @param permissionDTO the updated permission data
     * @return the updated permission
     * @throws ResponseStatusException if the permission is not found
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionDTO permissionDTO) {
        Permission existingPermission = permissionRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found"));

        Permission titledPermision = permissionRepository.findByTitle(permissionDTO.title()).orElse(null);

        if (titledPermision != null && titledPermision.getId() != id) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Permission already exists");
        }
        
        existingPermission.setTitle(permissionDTO.title());

        Permission updatedPermission = permissionRepository.save(existingPermission);

        return ResponseEntity.ok(updatedPermission.toDTO());
    }

    /**
     * Delete a permission
     * 
     * @param id the id of the permission to delete
     * @return no content
     * @throws ResponseStatusException if the permission is not found
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        Permission permission = permissionRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found"));

        permission.delete(); // Mark as deleted (soft delete)
        permissionRepository.save(permission);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

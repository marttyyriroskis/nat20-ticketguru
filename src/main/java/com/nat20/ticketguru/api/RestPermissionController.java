package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.repository.PermissionRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/permissions")
public class RestPermissionController {
    
    @Autowired
    private final PermissionRepository permissionRepository;

    public RestPermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    // Get all Permissions
    @GetMapping
    public Iterable<Permission> getPermissions() {
        return permissionRepository.findAll();
    }

    // Get Permission by id
    @GetMapping("/{id}")
    public Permission getPermission(@PathVariable Long id) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (!optionalPermission.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }
        return optionalPermission.get();
    }

    // Add a new Permission
    @PostMapping
    public ResponseEntity<Permission> addPermission(@RequestBody Permission permission) {
        permissionRepository.save(permission);
        if (permission.getTitle() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Permission title is required");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(permission);
    }

    // Update a Permission
    @PutMapping("/{id}")
    public Permission updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (!optionalPermission.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }
        permission.setTitle(permission.getTitle());
        return permissionRepository.save(permission);
    }

    // Delete a Permission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (!optionalPermission.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Permission not found");
        }
        permissionRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}

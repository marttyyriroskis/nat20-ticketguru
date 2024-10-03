package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.repository.RoleRepository;
import com.nat20.ticketguru.repository.PermissionRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/roles")
public class RestRoleController {
    
    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PermissionRepository permissionRepository;

    public RestRoleController(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    // Get all roles
    @GetMapping
    public Iterable<Role> getRoles() {
        return roleRepository.findAll();
    }

    // Get role by id
    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }
        return optionalRole.get();
    }

    // Add a new role
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        roleRepository.save(role);
        if (role.getTitle() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Role title is required");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    // Update a role
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }
        role.setTitle(role.getTitle());
        return roleRepository.save(role);
    }

    // Delete a role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role not found");
        }
        roleRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Add a permission to a role
    @PostMapping("/{id}/permissions")
        public ResponseEntity<Role> addPermissionToRole(@PathVariable Long id, @RequestBody Permission permissionRequest) {
        Long permissionId = permissionRequest.getId();
        
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (!optionalPermission.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Role role = optionalRole.get();
        Permission permission = optionalPermission.get();

        role.addPermission(permission);

        roleRepository.save(role);

        return ResponseEntity.ok(role);
    }

    // Remove a permission from a role
    @DeleteMapping("/{id}/permissions/{permissionId}")
    public ResponseEntity<Role> removePermissionFromRole(@PathVariable Long id, @PathVariable Long permissionId) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        if (!optionalPermission.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Role role = optionalRole.get();
        Permission permission = optionalPermission.get();

        role.removePermission(permission);

        roleRepository.save(role);

        return ResponseEntity.ok(role);
    }

    
}

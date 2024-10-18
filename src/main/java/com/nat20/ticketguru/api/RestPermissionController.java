package com.nat20.ticketguru.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.dto.PermissionDTO;
import com.nat20.ticketguru.repository.PermissionRepository;
import com.nat20.ticketguru.repository.RoleRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/permissions")
@Validated
public class RestPermissionController {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public RestPermissionController(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    // Get all Permissions
    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getPermissions() {
        Iterable<Permission> iterablePermissions = permissionRepository.findAll();
        List<Permission> permissionList = new ArrayList<>();
        iterablePermissions.forEach(permissionList::add);

        return ResponseEntity.ok(permissionList.stream()
            .map(permission -> new PermissionDTO(
                permission.getTitle(),
                permission.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet())))
            .collect(Collectors.toList()));
    }

    // Get Permission by id
    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable Long id) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permission not found"));
        
        return ResponseEntity.ok(new PermissionDTO(
                    permission.getTitle(),
                    permission.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet())));
    }

    // Add a new Permission
    @PostMapping
    public ResponseEntity<PermissionDTO> addPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        // TODO: Validation
        Permission permission = new Permission();
        permission.setTitle(permissionDTO.title());

        Iterable<Role> iterableRoles = roleRepository.findAll();
        Set<Role> roleList = new HashSet<>();
        iterableRoles.forEach(roleList::add);
        permission.setRoles(roleList);

        Permission newPermission = permissionRepository.save(permission);

        PermissionDTO newPermissionDTO = new PermissionDTO(
                newPermission.getTitle(),
                newPermission.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet()));

        return ResponseEntity.status(HttpStatus.CREATED).body(newPermissionDTO);
    }

    // Update a Permission
    @PutMapping("/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        Optional<Permission> existingPermission = permissionRepository.findById(id);

        if (!existingPermission.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Permission permissionToUpdate = existingPermission.get();

        permissionToUpdate.setTitle(permissionDTO.title());

        Iterable<Role> iterableRoles = roleRepository.findAll();
        Set<Role> roleList = new HashSet<>();
        iterableRoles.forEach(roleList::add);
        permissionToUpdate.setRoles(roleList);

        Permission updatedPermission = permissionRepository.save(permissionToUpdate);

        PermissionDTO updatedPermissionDTO = new PermissionDTO(
            updatedPermission.getTitle(),
            updatedPermission.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet()));

        return ResponseEntity.ok(updatedPermissionDTO);
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

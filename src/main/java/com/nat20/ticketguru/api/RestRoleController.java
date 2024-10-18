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
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.dto.RoleDTO;
import com.nat20.ticketguru.repository.RoleRepository;
import com.nat20.ticketguru.repository.UserRepository;

import jakarta.validation.Valid;

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
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public RestRoleController(RoleRepository roleRepository, PermissionRepository permissionRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getRoles() {
        Iterable<Role> iterableRoles = roleRepository.findAll();
        List<Role> roleList = new ArrayList<>();
        iterableRoles.forEach(roleList::add);

        return ResponseEntity.ok(roleList.stream()
            .map(role -> new RoleDTO(
                role.getTitle(),
                role.getPermissions().stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet()),
                role.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList())))
            .collect(Collectors.toList()));
    }

    // Get role by id
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        
        return ResponseEntity.ok(new RoleDTO(
                role.getTitle(),
                role.getPermissions().stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet()),
                role.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList())));
    }

    // Add a new role
    @PostMapping
    public ResponseEntity<RoleDTO> addRole(@Valid @RequestBody RoleDTO roleDTO) {
        // TODO: Validation
        Role role = new Role();
        role.setTitle(roleDTO.title());

        Iterable<Permission> iterablePermissions = permissionRepository.findAll();
        Set<Permission> permissionSet = new HashSet<>();
        iterablePermissions.forEach(permissionSet::add);
        role.setPermissions(permissionSet);

        Iterable<User> iterableUsers = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);
        role.setUsers(userList);

        Role newRole = roleRepository.save(role);

        RoleDTO newRoleDTO = new RoleDTO(
                newRole.getTitle(),
                newRole.getPermissions().stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet()),
                newRole.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED).body(newRoleDTO);
    }
    
    // Update a role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (!existingRole.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Role roleToUpdate = existingRole.get();

        roleToUpdate.setTitle(roleDTO.title());

        Iterable<Permission> iterablePermissions = permissionRepository.findAll();
        Set<Permission> permissionSet = new HashSet<>();
        iterablePermissions.forEach(permissionSet::add);
        roleToUpdate.setPermissions(permissionSet);

        Iterable<User> iterableUsers = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);
        roleToUpdate.setUsers(userList);

        Role updatedRole = roleRepository.save(roleToUpdate);

        RoleDTO updatedRoleDTO = new RoleDTO(
                updatedRole.getTitle(),
                updatedRole.getPermissions().stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet()),
                updatedRole.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList()));

        return ResponseEntity.ok(updatedRoleDTO);
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

        if (!role.hasPermission(permission)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        role.removePermission(permission);

        roleRepository.save(role);

        return ResponseEntity.ok(role);
    }

    
}

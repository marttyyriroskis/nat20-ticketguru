package com.nat20.ticketguru.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.repository.RoleRepository;
import com.nat20.ticketguru.repository.UserRepository;

import com.nat20.ticketguru.dto.UserDTO;
import com.nat20.ticketguru.dto.UserCreateDTO;

import jakarta.validation.Valid;

import java.util.List;

/**
 * REST controller for users
 * 
 * @author Jesse Hellman
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
@Validated
public class UserRestController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRestController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Get all users
     * 
     * @return all users
     */
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public ResponseEntity<Iterable<UserDTO>> getUsers() {
        List<User> users = userRepository.findAllActive();

        return ResponseEntity.ok(users.stream()
            .map(User::toDTO)
            .toList());
    }

    /**
     * Get a user by id
     * 
     * @param id the id of the user requested
     * @return the user requested
     * @exception ResponseStatusException if unauthorized
     * @exception ResponseStatusException if user not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id, @AuthenticationPrincipal User user) {

        if (user.getId() != id && !user.getRole().getTitle().equals("ADMIN")) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You are not authorized to view this user");
        }

        User getUser = userRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok(getUser.toDTO());
    }

    /**
     * Add a user
     * 
     * @param ucDTO the user to add
     * @return the user added
     * @exception ResponseStatusException if role not found
     */
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserCreateDTO ucDTO) {

        if (userRepository.findByEmail(ucDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }

        User user = ucDTO.toUser();

        if (ucDTO.getRoleId() != null) {

            Role existingRole = roleRepository.findByIdActive(
                ucDTO.getRoleId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

            user.setRole(existingRole);
        }

        if (ucDTO.getPassword() != null) {
            String hashedPassword = BCrypt.hashpw(ucDTO.getPassword(), BCrypt.gensalt());
            user.sethashedPassword(hashedPassword);
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.toDTO());
    }

    /**
     * Update a user
     * 
     * @param id the id of the user to be updated
     * @param editedUser the requested updates for the user
     * @return the updated user
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_USERS')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO editedUser) {
        
        User user = userRepository.findByIdActive(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (editedUser.getEmail() != null) {
            User existingUser = userRepository.findByEmail(editedUser.getEmail()).orElse(null);
            if (existingUser != null && existingUser.getId() != id) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
            }
        }

        user.setEmail(editedUser.getEmail());
        user.setFirstName(editedUser.getFirstName());
        user.setLastName(editedUser.getLastName());

        if (editedUser.getRoleId() != null) {
            Role existingRole = roleRepository.findByIdActive(
                editedUser.getRoleId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));
            user.setRole(existingRole);
        }

        if (editedUser.getPassword() != null) {
            String hashedPassword = BCrypt.hashpw(editedUser.getPassword(), BCrypt.gensalt());
            user.sethashedPassword(hashedPassword);
        }

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser.toDTO());
    }

    /**
     * Delete a user
     * 
     * @param id the id of the user to be deleted
     * @return 204 No Content
     * @exception ResponseStatusException if user not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {

        User user = userRepository.findByIdActive(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        user.delete(); // Mark as deleted (soft delete)
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

package com.nat20.ticketguru.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    public RestUserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Get all users
    @GetMapping
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    // Get user by id
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }
        User user = optionalUser.get();
        return user;
    }

    // Add a new user
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {

        if (user.getRole() != null && user.getRole().getId() == null) {
            user.setRole(null);
        }

        if (user.getRole() != null) {
            Optional<Role> existingRole = roleRepository.findById(user.getRole().getId());

            if (!existingRole.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Role not found");
            }

            user.setRole(existingRole.get());
        } else {
            user.setRole(null);
        }

        // Uncomment the following when spring security is added
        // String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // user.setPassword(hashedPassword);
        User response = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update a user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User editedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }
        User user = optionalUser.get();
        user.setEmail(editedUser.getEmail());
        user.setFirstName(editedUser.getFirstName());
        user.setLastName(editedUser.getLastName());
        user.setRole(editedUser.getRole()); // Validate?
        // Uncomment the following when spring security is added
        // String hashedPassword = BCrypt.hashpw(editedUser.getPassword(), BCrypt.gensalt());
        // user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}

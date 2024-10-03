package com.nat20.ticketguru.api;

// import org.springframework.security.crypto.bcrypt.BCrypt; // Uncomment when spring security is added

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    @Autowired
    private UserRepository userRepository;

    RestUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User addUser(@RequestBody User user) {
        // Uncomment the following when spring security is added
        // String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // user.setPassword(hashedPassword);
        return userRepository.save(user);
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
        // Uncomment the following when spring security is added
        // String hashedPassword = BCrypt.hashpw(editedUser.getPassword(), BCrypt.gensalt());
        // user.setPassword(hashedPassword);
        return userRepository.save(user);
    }
    
    // Delete a user
    @DeleteMapping("/{id}")
    public Iterable<User> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
        return userRepository.findAll();
    }
    
}

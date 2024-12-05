package com.nat20.ticketguru.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.repository.UserRepository;

/**
 * Service class for UserDetails
 * 
 * @author Julia Hämäläinen
 * @version 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username (email).
     * Uses Spring Security for authentication.
     * 
     * @param email the username (email) of the user to load
     * @return a `UserDetails` object representing the authenticated user
     * @throws UsernameNotFoundException if a user with the given email is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username"));

        return currentUser;
    }

}

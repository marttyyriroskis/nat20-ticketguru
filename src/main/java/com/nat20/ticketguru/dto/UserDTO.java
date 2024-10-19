package com.nat20.ticketguru.dto;

import com.nat20.ticketguru.domain.Role;

public record UserDTO(Long id, String email, String firstName, String lastName, Role role) {
    
}

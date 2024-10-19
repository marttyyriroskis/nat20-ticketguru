package com.nat20.ticketguru.dto;

public record UserDTO(Long id, String email, String firstName, String lastName, RoleDTO role) {
    
}

package com.nat20.ticketguru.dto;

import java.util.List;

public record UserDTO(Long id, String email, String firstName, String lastName, RoleDTO role, List<Long> saleIds) {
    
}

package com.nat20.ticketguru.dto;

import java.util.List;

public record UserDTO(String email, String firstName, String lastName, RoleDTO role, List<Long> saleIds) {
    
}

package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(
    Long id,
    
    @NotEmpty
    @Size(max = 150)
    String email,
    
    @NotEmpty
    @Size(max = 150)
    String firstName,
    
    @NotEmpty
    @Size(max = 150)
    String lastName,
    
    RoleDTO role, List<Long> saleIds) {
    
}

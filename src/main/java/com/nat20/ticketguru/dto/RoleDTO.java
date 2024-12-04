package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RoleDTO(
        Long id,
        
        @NotEmpty(message = "Title must not be empty")
        @Size(max = 50)
        String title,
        
        List<String> permissions,
        List<Long> userIds) {

}

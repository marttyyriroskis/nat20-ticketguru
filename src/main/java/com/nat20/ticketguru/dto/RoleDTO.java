package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record RoleDTO(
        @NotEmpty(message = "Title must not be empty")
        String title,
        
        List<String> permissions,
        List<Long> userIds) {

}

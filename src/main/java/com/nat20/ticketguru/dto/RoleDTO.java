package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record RoleDTO(
        @NotNull(message = "Title must not be null")
        String title,
        List<String> permissions,
        List<Long> userIds) {

}

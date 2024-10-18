package com.nat20.ticketguru.dto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record RoleDTO(
    @NotNull(message = "Title must not be null") String title,

    Set<Long> permissionIds,

    List<Long> userIds
) {}

package com.nat20.ticketguru.dto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record RoleDTO(
    @NotNull(message = "Title must not be null") String title,

    @NotNull(message = "Permissions must not be null") Set<Long> permissionIds,

    @NotNull(message = "Users must not be null") List<Long> userIds
) {}

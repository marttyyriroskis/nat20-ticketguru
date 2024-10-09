package com.nat20.ticketguru.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record PermissionDTO(
    @NotNull(message = "Title must not be null") String title,

    @NotNull(message = "Roles must not be null") Set<Long> roleIds
) {}

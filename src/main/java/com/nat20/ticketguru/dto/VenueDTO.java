package com.nat20.ticketguru.dto;

import jakarta.validation.constraints.NotNull;

public record VenueDTO(
                Long id,
                @NotNull(message = "Name must not be null") String name,
                @NotNull(message = "Address must not be null") String address,
                String zipcode
    ) {
    

}

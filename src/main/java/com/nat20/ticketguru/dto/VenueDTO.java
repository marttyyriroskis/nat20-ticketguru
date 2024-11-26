package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record VenueDTO(
                @NotEmpty(message = "Name must not be empty")
                @Size(min = 1, max = 100)
                String name,

                @NotEmpty(message = "Address must not be empty")
                @Size(min = 1, max = 100)
                String address,
                
                @NotEmpty
                String zipcode,

                List<Long> eventIds
    ) {
    

}

package com.nat20.ticketguru.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record VenueDTO(
                Long id,

                @NotEmpty(message = "Name must not be empty")
                @Size(max = 100)
                String name,

                @NotEmpty(message = "Address must not be empty")
                @Size(max = 100)
                String address,
                
                @NotEmpty
                @Size(max = 5)
                String zipcode,

                List<Long> eventIds
    ) {
    

}

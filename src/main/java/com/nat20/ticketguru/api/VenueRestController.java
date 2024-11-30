package com.nat20.ticketguru.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.domain.Zipcode;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.repository.ZipcodeRepository;
import com.nat20.ticketguru.dto.VenueDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/venues")
@Validated
public class VenueRestController {

    private final VenueRepository venueRepository;
    private final ZipcodeRepository zipcodeRepository;

    public VenueRestController(VenueRepository venueRepository, ZipcodeRepository zipcodeRepository) {
        this.venueRepository = venueRepository;
        this.zipcodeRepository = zipcodeRepository;
    }

    // Get venues
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_VENUES')")
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        
        List<Venue> venues = venueRepository.findAllActive();

        return ResponseEntity.ok(venues.stream()
                .map(Venue::toDTO)
                .toList());
    }

    // Get venue by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_VENUES')")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {

        Venue venue = venueRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

            return ResponseEntity.ok(venue.toDTO());
    }

    // Post a new venue
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_VENUES')")
    public ResponseEntity<VenueDTO> createVenue(@Valid @RequestBody VenueDTO venueDTO) {

        Zipcode zipcode = zipcodeRepository.findById(venueDTO.zipcode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Zipcode"));

        Venue venue = new Venue();
        venue.setName(venueDTO.name());
        venue.setAddress(venueDTO.address());
        venue.setZipcode(zipcode);

        Venue addedVenue = venueRepository.save(venue);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedVenue.toDTO());

    }

    // Edit venue with PUT request
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_VENUES')")
    public ResponseEntity<VenueDTO> editVenue(@Valid @RequestBody VenueDTO venueDTO, @PathVariable Long id) {

        Venue venue = venueRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

        Zipcode zipcode = zipcodeRepository.findById(venueDTO.zipcode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Zipcode"));

        venue.setName(venueDTO.name());
        venue.setAddress(venueDTO.address());
        venue.setZipcode(zipcode);

        Venue editedVenue = venueRepository.save(venue);

        return ResponseEntity.status(HttpStatus.CREATED).body(editedVenue.toDTO());

    }

    // Delete venue with DELETE Request
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_VENUES')")
    public ResponseEntity<String> deleteVenue(@PathVariable Long id) {

        Venue venue = venueRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

        venue.delete();

        venueRepository.save(venue);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}

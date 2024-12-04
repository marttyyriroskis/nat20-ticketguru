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

/**
 * REST controller for venues
 * 
 * @author Tomi Lappalainen
 * @version 1.0
 */
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

    /**
     * Get all venues
     * 
     * @return all venues
     */
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_VENUES')")
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        
        List<Venue> venues = venueRepository.findAllActive();

        return ResponseEntity.ok(venues.stream()
                .map(Venue::toDTO)
                .toList());
    }

    /**
     * Get a venue by id
     * 
     * @param id the id of the venue requested
     * @return the venue requested
     * @exception ResponseStatusException if unauthorized
     * @exception ResponseStatusException if venue not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_VENUES')")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {

        Venue venue = venueRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

            return ResponseEntity.ok(venue.toDTO());
    }

    /**
     * Add a venue
     * 
     * @param venueDTO the venue to add
     * @return the venue added
     * @exception ResponseStatusException if zipcode not found
     */
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

    /**
     * Update a venue
     * 
     * @param id the id of the venue to be updated
     * @param venueDTO the requested updates for the venue
     * @return the updated venue
     */
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

    /**
     * Delete a venue
     * 
     * @param id the id of the venue to be deleted
     * @return 204 No Content
     * @exception ResponseStatusException if venue not found
     */
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

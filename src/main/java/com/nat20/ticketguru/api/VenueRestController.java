package com.nat20.ticketguru.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("")
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        List<Venue> venues = new ArrayList<Venue>();
        venueRepository.findAll().forEach(venues::add);

        List<VenueDTO> venueDTOs = venues.stream()
                .filter(venue -> venue.getDeletedAt() == null)
                .map(venue -> new VenueDTO(
                venue.getId(),
                venue.getName(),
                venue.getAddress(),
                venue.getZipcode().getZipcode()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(venueDTOs);
    }

    // Get venue by id
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable("id") Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

        if (venue.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
        } else {
            VenueDTO venueDTO = new VenueDTO(
                    venue.getId(),
                    venue.getName(),
                    venue.getAddress(),
                    venue.getZipcode().getZipcode());
            return ResponseEntity.ok(venueDTO);
        }
    }

    // Post a new venue
    @PostMapping("")
    public ResponseEntity<VenueDTO> createVenue(@Valid @RequestBody VenueDTO venueDTO) {

        // check if zipcode is null
        if (venueDTO.zipcode() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Zipcode cannot be null!");
        } // if zipcode is not null, check if it is already exists
        else {
            Optional<Zipcode> existingZipcode = zipcodeRepository.findById(venueDTO.zipcode());

            if (!existingZipcode.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Zipcode does not exist!");
            } else {

                Venue venue = new Venue(
                        venueDTO.name(),
                        venueDTO.address(),
                        existingZipcode.get(),
                        null);

                Venue savedVenue = venueRepository.save(venue);

                VenueDTO responseDTO = new VenueDTO(
                        savedVenue.getId(),
                        savedVenue.getName(),
                        savedVenue.getAddress(),
                        savedVenue.getZipcode().getZipcode());

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            }
        }

    }

    // Edit venue with PUT request
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> editVenue(@Valid @RequestBody VenueDTO venueDTO, @PathVariable("id") Long venueId) {

        Optional<Venue> existingVenue = venueRepository.findById(venueId);
        if (!existingVenue.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Venue not found");
        } else {
            if (existingVenue.get().getDeletedAt() != null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found");
            } else {
                Optional<Zipcode> existingZipcode = zipcodeRepository.findById(venueDTO.zipcode());

                Venue editedVenue = existingVenue.get();
                editedVenue.setName(venueDTO.name());
                editedVenue.setAddress(venueDTO.address());

                Zipcode editedZipcode = editedVenue.getZipcode();
                if (editedZipcode != null) {

                    if (!existingZipcode.isPresent()) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Invalid zipcode");
                    }
                    editedVenue.setZipcode(existingZipcode.get());
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Zipcode cannot be null!");
                }
                if (!existingZipcode.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zipcode not not found");
                }

                Venue savedVenue = venueRepository.save(editedVenue);

                VenueDTO responseDTO = new VenueDTO(
                        savedVenue.getId(),
                        savedVenue.getName(),
                        savedVenue.getAddress(),
                        savedVenue.getZipcode().getZipcode());

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
            }
        }

    }

    // Delete venue with DELETE Request
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable("id") Long venueId) {
        // Finds the venue with the mapped id from the repository; assigns null if not
        // found
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        // Checks if the venue is null or not null
        if (!optionalVenue.isPresent() || optionalVenue.get().getDeletedAt() != null) {
            // If null (ie. not found), throws exception and error message
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Venue not found");
        } else {

            optionalVenue.get().setDeletedAt(LocalDateTime.now());
            venueRepository.save(optionalVenue.get());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }

    }
}

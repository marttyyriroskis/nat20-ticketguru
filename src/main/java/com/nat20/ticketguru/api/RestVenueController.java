package com.nat20.ticketguru.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.domain.Zipcode;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.repository.ZipcodeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/venues")
@Validated
public class RestVenueController {

    private final VenueRepository venueRepository;
    private final ZipcodeRepository zipcodeRepository;
    
    public RestVenueController(VenueRepository venueRepository, ZipcodeRepository zipcodeRepository) {   
        this.venueRepository = venueRepository;
        this.zipcodeRepository = zipcodeRepository;
    }

    // Get venues
    @GetMapping("")
    public Iterable<Venue> getVenues() {
        return venueRepository.findAll();
    }

    // Get venue by id
    @GetMapping("/{id}")
    public Venue getvenue(@PathVariable("id") Long venueId) {
        Optional<Venue> optionalvenue = venueRepository.findById(venueId);
        if (!optionalvenue.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "venue not found");
        }
        Venue venue = optionalvenue.get();
        return venue;
    }

    // Post a new venue
    @PostMapping("")
    public Venue createVenue(@Valid @RequestBody Venue venue) {

        // check if zipcode is null
        if (venue.getZipcode() == null) {
            throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Zipcode cannot be null!");
        }
        // if zipcode is not null, check if it is already exists
        if (venue.getZipcode() != null) {
            Optional<Zipcode> existingZipcode = zipcodeRepository.findById(venue.getZipcode().getZipcode());

            if (!existingZipcode.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Zipcode does not exist!");
            }

            venue.setZipcode(existingZipcode.get());

        }
        Venue savedVenue = venueRepository.save(venue);
        return savedVenue;
    }


    // Edit venue with PUT request
    @PutMapping("/{id}")
    public Venue editVenue(@Valid @RequestBody Venue editedVenue, @PathVariable("id") Long venueId) {
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        if (!optionalVenue.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Venue not found");
        }
        Venue venue = optionalVenue.get();

        // update fields from editedVenue in a way that respects field validation rules
        venue.setName(editedVenue.getName());
        venue.setAddress(editedVenue.getAddress());
        
        

        // no changes to Zipcode fields allowed: can set zipcode to a
        // different existing zipcode
        Zipcode editedZipcode = editedVenue.getZipcode();
        if (editedZipcode != null) {
            Optional<Zipcode> existingZipcode = zipcodeRepository.findById(editedZipcode.getZipcode());
            if (!existingZipcode.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid zipcode");
            }
            venue.setZipcode(existingZipcode.get());
        } else {
            throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Zipcode cannot be null!");
        }

        return venueRepository.save(venue);
    }

    // Delete venue with DELETE Request
    @DeleteMapping("/{id}")
    public Iterable<Venue> deleteVenue(@PathVariable("id") Long venueId) {
        // Finds the venue with the mapped id from the repository; assigns null if not
        // found
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        // Checks if the venue is null or not null
        if (!optionalVenue.isPresent()) {
            // If null (ie. not found), throws exception and error message
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Venue not found");
        }
        // If not null (ie. found), deletes the venue with the mapped id from the
        // repository
        venueRepository.deleteById(venueId);
        // Returns all the remaining venues (without the removed venue)
        return venueRepository.findAll();
    }

    // Exception handler for validation errors
    // If validation fails, a MethodArgumentNotValidException is thrown,
    // which then returns the failed field(s) and the validation failure message(s)
    // as a BAD_REQUEST response
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
    // Source:
    // https://dev.to/shujaat34/exception-handling-and-validation-in-spring-boot-3of9
    // The source details a Global Exception handler, that we could implement later
    // to handle all the endpoints

}

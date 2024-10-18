package com.nat20.ticketguru.api;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PutMapping;

import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.TicketTypeDTO;
import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.EventRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickettypes")
@Validated
public class RestTicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public RestTicketTypeController(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }

    // Get all ticket types
    @GetMapping("")
    public ResponseEntity<List<TicketTypeDTO>> getAllTicketTypes() {
        List<TicketType> ticketTypes = new ArrayList<TicketType>();
        ticketTypeRepository.findAll().forEach(ticketTypes::add);

        List<TicketTypeDTO> ticketTypeDTOs = ticketTypes.stream()
                .map(ticketType -> new TicketTypeDTO(
                        ticketType.getId(),
                        ticketType.getName(),
                        ticketType.getRetail_price(),
                        ticketType.getTotal_available(),
                        ticketType.getEvent().getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ticketTypeDTOs);
    }

    // Get ticket type by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketTypeDTO> getTicketTypeById(@PathVariable("id") Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found"));

        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO(
                ticketType.getId(),
                ticketType.getName(),
                ticketType.getRetail_price(),
                ticketType.getTotal_available(),
                ticketType.getEvent().getId());

        return ResponseEntity.ok(ticketTypeDTO);
    }

    // Add a new ticket type
    @PostMapping
    public ResponseEntity<TicketTypeDTO> createTicketType(@Valid @RequestBody TicketTypeDTO ticketTypeDTO) {
        Optional<Event> existingEvent = eventRepository.findById(ticketTypeDTO.eventId());

        if (!existingEvent.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event does not exist!");
        } else {
            TicketType ticketType = new TicketType(
                    ticketTypeDTO.name(),
                    ticketTypeDTO.retail_price(),
                    ticketTypeDTO.total_available(),
                    existingEvent.get());

            TicketType savedTicketType = ticketTypeRepository.save(ticketType);

            TicketTypeDTO responseDTO = new TicketTypeDTO(
                    savedTicketType.getId(),
                    savedTicketType.getName(),
                    savedTicketType.getRetail_price(),
                    savedTicketType.getTotal_available(),
                    savedTicketType.getEvent().getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
    }

    // Update a ticket type
    @PutMapping("/{id}")
    public TicketType editTicketType(@PathVariable("id") Long ticketTypeId,
            @Valid @RequestBody TicketType ticketTypeBody) {
        Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(ticketTypeId);
        // Check if the ticket type specified in the PathVariable actually exists
        if (!optionalTicketType.isPresent()) {
            // If no, throws an exception
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found!");
        }

        // If yes,
        // Create a new TicketType object with attributes from PathVariable
        // Ie. existing attributs from the ticket type we want to edit (incl. id)
        TicketType editedTicketType = optionalTicketType.get();

        // Set the edited ticket type's attributes to match RequestBody
        editedTicketType.setName(ticketTypeBody.getName());
        editedTicketType.setRetail_price(ticketTypeBody.getRetail_price());
        editedTicketType.setTotal_available(ticketTypeBody.getTotal_available());

        Event editedEvent = ticketTypeBody.getEvent();
        // Checks that the event from the RequestBody exists
        if (editedEvent != null) {
            // If yes, finds the event in the event repository
            Optional<Event> existingEvent = eventRepository.findById(editedEvent.getId());
            // If event is not found
            if (!existingEvent.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!");
            }
            // If event is found, set the event to the edited ticket type
            editedTicketType.setEvent(existingEvent.get());
        } else {
            // If no, throws an exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event cannot be null!");
        }

        // Save the ticket type to ticketTypeRepository
        ticketTypeRepository.save(editedTicketType);
        return editedTicketType;
    }

    // Delete a ticket type
    @DeleteMapping("/{id}")
    public Iterable<TicketType> deleteTicketType(@PathVariable("id") Long ticketTypeId) {
        Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(ticketTypeId);
        if (!optionalTicketType.isPresent()) {
            // If null (ie. not found), throws exception and error message
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ticket type not found");
        }
        // If not null (ie. found), deletes the event with the mapped id from the
        // repository
        ticketTypeRepository.deleteById(ticketTypeId);
        // Returns all the remaining events (without the removed event)
        return ticketTypeRepository.findAll();
    }
}

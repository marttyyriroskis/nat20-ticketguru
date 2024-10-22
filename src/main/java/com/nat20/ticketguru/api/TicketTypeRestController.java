package com.nat20.ticketguru.api;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

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
public class TicketTypeRestController {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public TicketTypeRestController(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }

    // Get all ticket types
    @GetMapping("")
    public ResponseEntity<List<TicketTypeDTO>> getAllTicketTypes() {
        List<TicketType> ticketTypes = new ArrayList<TicketType>();
        ticketTypeRepository.findAll().forEach(ticketTypes::add);

        List<TicketTypeDTO> ticketTypeDTOs = ticketTypes.stream()
                .filter(ticketType -> ticketType.getDeletedAt() == null)
                .map(ticketType -> new TicketTypeDTO(
                        ticketType.getId(),
                        ticketType.getName(),
                        ticketType.getRetailPrice(),
                        ticketType.getTotalAvailable(),
                        ticketType.getEvent().getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ticketTypeDTOs);
    }

    // Get ticket type by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketTypeDTO> getTicketTypeById(@PathVariable("id") Long ticketTypeId) {
        Optional<TicketType> ticketType = ticketTypeRepository.findById(ticketTypeId);

        if (!ticketType.isPresent() || ticketType.get().getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found");
        } else {
            TicketType presentTicketType = ticketType.get();
            TicketTypeDTO ticketTypeDTO = new TicketTypeDTO(
                    presentTicketType.getId(),
                    presentTicketType.getName(),
                    presentTicketType.getRetailPrice(),
                    presentTicketType.getTotalAvailable(),
                    presentTicketType.getEvent().getId());

            return ResponseEntity.ok(ticketTypeDTO);
        }
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
                    ticketTypeDTO.retailPrice(),
                    ticketTypeDTO.totalAvailable(),
                    existingEvent.get(),
                    null);

            TicketType savedTicketType = ticketTypeRepository.save(ticketType);

            TicketTypeDTO responseDTO = new TicketTypeDTO(
                    savedTicketType.getId(),
                    savedTicketType.getName(),
                    savedTicketType.getRetailPrice(),
                    savedTicketType.getTotalAvailable(),
                    savedTicketType.getEvent().getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
    }

    // Update a ticket type
    @PutMapping("/{id}")
    public ResponseEntity<TicketTypeDTO> editTicketType(@Valid @RequestBody TicketTypeDTO ticketTypeDTO,
            @PathVariable("id") Long ticketTypeId) {
        Optional<TicketType> existingTicketType = ticketTypeRepository.findById(ticketTypeId);

        if (!existingTicketType.isPresent() || existingTicketType.get().getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found!");
        } else {
            Optional<Event> existingEvent = eventRepository.findById(ticketTypeDTO.eventId());
            if (!existingEvent.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found!");
            } else {
                TicketType editedTicketType = existingTicketType.get();
                editedTicketType.setName(ticketTypeDTO.name());
                editedTicketType.setRetailPrice(ticketTypeDTO.retailPrice());
                editedTicketType.setTotalAvailable(ticketTypeDTO.totalAvailable());
                editedTicketType.setEvent(existingEvent.get());

                TicketType savedTicketType = ticketTypeRepository.save(editedTicketType);

                TicketTypeDTO responseDTO = new TicketTypeDTO(
                        savedTicketType.getId(),
                        savedTicketType.getName(),
                        savedTicketType.getRetailPrice(),
                        savedTicketType.getTotalAvailable(),
                        savedTicketType.getEvent().getId());

                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
        }
    }

    // Delete a ticket type
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicketType(@PathVariable("id") Long ticketTypeId) {
        Optional<TicketType> existingTicketType = ticketTypeRepository.findById(ticketTypeId);
        if (!existingTicketType.isPresent() || existingTicketType.get().getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found");
        } else {
            existingTicketType.get().setDeletedAt((LocalDateTime.now()));
            ticketTypeRepository.save(existingTicketType.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
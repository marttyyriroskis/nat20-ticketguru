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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.TicketTypeDTO;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.service.TicketSummaryService;

import jakarta.validation.Valid;

/**
 * REST controller for ticket types
 * 
 * @author Janne Airaksinen
 * @version 1.0
 */
@RestController
@RequestMapping("/api/tickettypes")
@Validated
public class TicketTypeRestController {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;
    private final TicketSummaryService ticketSummaryService;

    public TicketTypeRestController(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository, TicketSummaryService ticketSummaryService) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
        this.ticketSummaryService = ticketSummaryService;
    }

    /**
     * Get all ticket types
     * 
     * @return all ticket types
     */
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_TICKET_TYPES')")
    public ResponseEntity<List<TicketTypeDTO>> getAllTicketTypes() {

        List<TicketType> ticketTypes = ticketTypeRepository.findAllActive();

        return ResponseEntity.ok(ticketTypes.stream()
                .map(TicketType::toDTO)
                .toList());
    }

    /**
     * Get a ticket type by id
     * 
     * @param id the id of the ticket type requested
     * @return the ticket type requested
     * @exception ResponseStatusException if unauthorized
     * @exception ResponseStatusException if ticket type not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_TICKET_TYPES')")
    public ResponseEntity<?> getTicketTypeById(@PathVariable Long id) {

        TicketType ticketType = ticketTypeRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found"));
        Integer availableTickets = ticketSummaryService.countAvailableTicketsForTicketType(id);

        return ResponseEntity.ok(ticketType.toDTO(availableTickets));
    }

    /**
     * Retrieves a list of active ticket types for a specified event, including their available ticket counts
     *
     * @param eventId the ID of the event for which ticket types are to be retrieved
     * @return a ResponseEntity containing a list of TicketTypeDTOs with available ticket counts and status 200 OK
     * @throws ResponseStatusException if event not found
     */
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('VIEW_TICKET_TYPES')")
    public ResponseEntity<List<TicketTypeDTO>> searchTicketTypes(@RequestParam Long eventId) {
        eventRepository.findByIdActive(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event does not exist!"));

        List<TicketType> ticketTypes = ticketTypeRepository.findByEventIdActive(eventId);
        return ResponseEntity.ok(ticketTypes.stream()
                .map(ticketType -> ticketSummaryService.toDTOWithAvailableTickets(ticketType))
                .toList());
    }

    /**
     * Add a ticket type
     * 
     * @param ticketTypeDTO the ticket type to add
     * @return the ticket type added
     * @exception ResponseStatusException if event not found
     */
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TICKET_TYPES')")
    public ResponseEntity<TicketTypeDTO> createTicketType(@Valid @RequestBody TicketTypeDTO ticketTypeDTO) {

        Event event = eventRepository.findByIdActive(ticketTypeDTO.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Event ID"));

        TicketType ticketType = new TicketType();
        ticketType.setName(ticketTypeDTO.name());
        ticketType.setRetailPrice(ticketTypeDTO.retailPrice());
        ticketType.setTotalTickets(ticketTypeDTO.totalTickets());
        ticketType.setAvailableTickets(ticketTypeDTO.availableTickets());
        ticketType.setEvent(event);

        TicketType savedTicketType = ticketTypeRepository.save(ticketType);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicketType.toDTO());
    }

    /**
     * Update a ticket type
     * 
     * @param id the id of the ticket type to be updated
     * @param ticketTypeDTO the requested updates for the ticket type
     * @return the updated ticket type
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_TICKET_TYPES')")
    public ResponseEntity<TicketTypeDTO> editTicketType(@Valid @RequestBody TicketTypeDTO ticketTypeDTO, @PathVariable Long id) {

        TicketType ticketType = ticketTypeRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found"));

        Event event = eventRepository.findByIdActive(ticketTypeDTO.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Event ID"));

        ticketType.setName(ticketTypeDTO.name());
        ticketType.setRetailPrice(ticketTypeDTO.retailPrice());
        ticketType.setTotalTickets(ticketTypeDTO.totalTickets());
        ticketType.setEvent(event);

        TicketType savedTicketType = ticketTypeRepository.save(ticketType);

        return ResponseEntity.status(HttpStatus.OK).body(savedTicketType.toDTO());
    }

    /**
     * Delete a ticket type
     * 
     * @param id the id of the ticket type to be deleted
     * @return 204 No Content
     * @exception ResponseStatusException if ticket type not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TICKET_TYPES')")
    public ResponseEntity<String> deleteTicketType(@PathVariable Long id) {

        TicketType ticketType = ticketTypeRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket type not found"));

        // disallow deleting tickettype if it has any sold and non-deleted tickets
        if (ticketSummaryService.hasSoldNonDeletedTickets(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete ticket type because it has sold tickets.");
        }
        ticketType.delete();

        ticketTypeRepository.save(ticketType);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

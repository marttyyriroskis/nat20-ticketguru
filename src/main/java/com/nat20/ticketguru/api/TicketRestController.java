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

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.TicketDTO;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;

import jakarta.validation.Valid;

/**
 * REST controller for tickets
 * 
 * @author Julia Hämäläinen
 * @version 1.0
 */
@RestController
@RequestMapping("/api/tickets")
@Validated
public class TicketRestController {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final SaleRepository saleRepository;

    public TicketRestController(TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository, SaleRepository saleRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.saleRepository = saleRepository;
    }

    /**
     * Retrieves a list of tickets. If a list of IDs is provided, it fetches only the tickets with those IDs.
     * If no IDs are provided, it retrieves all active tickets
     *
     * @param ids an optional list of ticket IDs to filter the results
     * @return a ResponseEntity containing a list of TicketDTOs and a status of 200 (OK)
     * @throws ResponseStatusException if requested tickets not found
     */
    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_TICKETS')")
    public ResponseEntity<List<TicketDTO>> getTickets(@RequestParam(required = false) List<Long> ids) {
        List<Ticket> ticketList;
        if (ids != null && !ids.isEmpty()) {
            // get tickets by the list of Id's
            try {
                ticketList = ticketRepository.findAllByIdIn(ids);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
        } else {
            // get all tickets
            ticketList = ticketRepository.findAllActive();
        }

        return ResponseEntity.ok(ticketList.stream()
                .map(Ticket::toDTO)
                .toList());
    }

    /**
     * Get a ticket by id
     * 
     * @param id the id of the ticket requested
     * @return the ticket requested
     * @exception ResponseStatusException if unauthorized
     * @exception ResponseStatusException if ticket not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_TICKETS')")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {

        Ticket ticket = ticketRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        return ResponseEntity.ok(ticket.toDTO());
    }

    /**
     * Get a ticket by barcode
     * 
     * @param barcode the barcode of the ticket requested
     * @return the ticket requested
     * @exception ResponseStatusException if unauthorized
     * @exception ResponseStatusException if ticket not found
     */
    @PreAuthorize("hasAuthority('VIEW_TICKETS')")
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<TicketDTO> getTicketByBarcode(@PathVariable String barcode) {

        Ticket ticket = ticketRepository.findByBarcode(barcode);

        if (ticket == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }

        return ResponseEntity.ok(ticket.toDTO());
    }

    /**
     * Marks a ticket as used based on its barcode
     *
     * @param barcode the barcode of the ticket to be used
     * @return a ResponseEntity containing the updated TicketDTO and a status of 200 (OK)
     * @throws ResponseStatusException if ticket not found
     * @throws ResponseStatusException if ticket already used
     */
    @PreAuthorize("hasAuthority('USE_TICKETS')")
    @PutMapping("/use/{barcode}")
    public ResponseEntity<TicketDTO> useTicket(@PathVariable String barcode) {

        Ticket ticket = ticketRepository.findByBarcode(barcode);

        if (ticket == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }

        if (ticket.getUsedAt() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket already used");
        }

        ticket.use();

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ResponseEntity.ok(updatedTicket.toDTO());
    }

    /**
     * Add a ticket
     * 
     * @param ticketDTO the ticket to add
     * @return the ticket added
     * @exception ResponseStatusException if ticket type not found
     * @exception ResponseStatusException if sale not found
     */
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TICKETS')")
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {

        TicketType ticketType = ticketTypeRepository.findByIdActive(ticketDTO.ticketTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Type not found"));

        Sale sale = saleRepository.findByIdActive(ticketDTO.saleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        Ticket newTicket = new Ticket();
        newTicket.setUsedAt(ticketDTO.usedAt());
        newTicket.setPrice(ticketDTO.price());
        newTicket.setTicketType(ticketType);
        newTicket.setSale(sale);

        Ticket addedTicket = ticketRepository.save(newTicket);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedTicket.toDTO());
    }

    /**
     * Update a ticket
     * 
     * @param id the id of the ticket to be updated
     * @param ticketDTO the requested updates for the ticket
     * @return the updated ticket
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_TICKETS')")
    public ResponseEntity<TicketDTO> updateTicket(@Valid @RequestBody TicketDTO ticketDTO, @PathVariable Long id) {

        Ticket ticket = ticketRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        TicketType ticketType = ticketTypeRepository.findByIdActive(ticketDTO.ticketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid TicketType ID"));

        Sale sale = saleRepository.findByIdActive(ticketDTO.saleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Sale ID"));

        ticket.setUsedAt(ticketDTO.usedAt());
        ticket.setPrice(ticketDTO.price());
        ticket.setTicketType(ticketType);
        ticket.setSale(sale);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ResponseEntity.ok(updatedTicket.toDTO());
    }

    /**
     * Delete a ticket
     * 
     * @param id the id of the ticket to be deleted
     * @return 204 No Content
     * @exception ResponseStatusException if ticket not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TICKETS')")
    public ResponseEntity<TicketDTO> deleteTicket(@PathVariable Long id) {

        Ticket ticket = ticketRepository.findByIdActive(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        ticket.delete();

        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

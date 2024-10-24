package com.nat20.ticketguru.api;

import java.util.ArrayList;
import java.util.List;

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

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.TicketDTO;
import com.nat20.ticketguru.repository.TicketTypeRepository;

import jakarta.validation.Valid;

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

    // Get tickets
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getTickets() {
        Iterable<Ticket> iterableTickets = ticketRepository.findAllActive();
        List<Ticket> ticketList = new ArrayList<>();
        iterableTickets.forEach(ticketList::add);

        return ResponseEntity.ok(ticketList.stream()
                .map(Ticket::toDTO)
                .toList());
    }

    // Get ticket by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        return ResponseEntity.ok(ticket.toDTO());
    }
    
    // Post a new ticket
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        TicketType ticketType = ticketTypeRepository.findById(ticketDTO.ticketTypeId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Ticket Type not found"));
        Sale sale = saleRepository.findById(ticketDTO.saleId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sale not found"));

        Ticket newTicket = new Ticket();
        newTicket.setUsedAt(ticketDTO.usedAt());
        newTicket.setPrice(ticketDTO.price());
        newTicket.setTicketType(ticketType);
        newTicket.setSale(sale);

        ticketRepository.save(newTicket);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTicket.toDTO());
    }

    // Edit ticket
    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@Valid @RequestBody TicketDTO ticketDTO, @PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        ticket.setUsedAt(ticketDTO.usedAt());
        ticket.setPrice(ticketDTO.price());

        TicketType ticketType = ticketTypeRepository.findById(ticketDTO.ticketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid TicketType ID"));
        Sale sale = saleRepository.findById(ticketDTO.saleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Sale ID"));

        ticket.setTicketType(ticketType);
        ticket.setSale(sale);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ResponseEntity.ok(updatedTicket.toDTO());
    }

    // Delete ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<TicketDTO> deleteTicket(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        ticket.delete();

        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

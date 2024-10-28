package com.nat20.ticketguru.api;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Iterable<Ticket> iterableTickets = ticketRepository.findAll();
        List<Ticket> ticketList = new ArrayList<>();
        iterableTickets.forEach(ticketList::add);

        return ResponseEntity.ok(ticketList.stream()
                .map(ticket -> new TicketDTO(
                    ticket.getBarcode(),
                    ticket.getUsedAt(),
                    ticket.getPrice(),
                    ticket.getDeletedAt(),
                    ticket.getTicketType().getId(),
                    ticket.getSale().getId()))
                .filter(t -> t.deletedAt() == null)
                .collect(Collectors.toList()));
    }
    
    // Get ticket by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);

        if (!existingTicket.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = existingTicket.get();

        return ResponseEntity.ok(new TicketDTO(
                    ticket.getBarcode(),
                    ticket.getUsedAt(),
                    ticket.getPrice(),
                    ticket.getDeletedAt(),
                    ticket.getTicketType().getId(),
                    ticket.getSale().getId()));
    }
    
    // Post a new ticket
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        Optional<TicketType> existingTicketType = ticketTypeRepository.findById(ticketDTO.ticketTypeId());

        if (!existingTicketType.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TicketType ticketType = existingTicketType.get();

        Optional<Sale> existingSale = saleRepository.findById(ticketDTO.saleId());

        if (!existingSale.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Sale sale = existingSale.get();

        Ticket newTicket = new Ticket();
        newTicket.setUsedAt(ticketDTO.usedAt());
        newTicket.setPrice(ticketDTO.price());
        newTicket.setTicketType(ticketType);
        newTicket.setSale(sale);

        ticketRepository.save(newTicket);

        TicketDTO newTicketDTO = new TicketDTO(
                newTicket.getBarcode(),
                newTicket.getUsedAt(),
                newTicket.getPrice(),
                newTicket.getDeletedAt(),
                newTicket.getTicketType().getId(),
                newTicket.getSale().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(newTicketDTO);
    }

    // Edit ticket
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        
        if (!existingTicket.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticketToUpdate = existingTicket.get();

        ticketToUpdate.setUsedAt(ticketDTO.usedAt());
        ticketToUpdate.setPrice(ticketDTO.price());

        Optional<TicketType> existingTicketType = ticketTypeRepository.findById(ticketDTO.ticketTypeId());

        if (!existingTicketType.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TicketType ticketType = existingTicketType.get();

        Optional<Sale> existingSale = saleRepository.findById(ticketDTO.saleId());

        if (!existingSale.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Sale sale = existingSale.get();

        ticketToUpdate.setTicketType(ticketType);
        ticketToUpdate.setSale(sale);

        Ticket updatedTicket = ticketRepository.save(ticketToUpdate);

        TicketDTO updatedTicketDTO = new TicketDTO(
                updatedTicket.getBarcode(),
                updatedTicket.getUsedAt(),
                updatedTicket.getPrice(),
                updatedTicket.getDeletedAt(),
                updatedTicket.getTicketType().getId(),
                updatedTicket.getSale().getId()
        );

        return ResponseEntity.ok(updatedTicketDTO);
    }

    // Delete ticket
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TicketDTO> deleteTicket(@PathVariable Long id) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);

        if (!existingTicket.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticketToDelete = existingTicket.get();
        ticketToDelete.setDeletedAt(LocalDateTime.now());

        Ticket deletedTicket = ticketRepository.save(ticketToDelete);

        TicketDTO deletedTicketDTO = new TicketDTO(
                deletedTicket.getBarcode(),
                deletedTicket.getUsedAt(),
                deletedTicket.getPrice(),
                deletedTicket.getDeletedAt(),
                deletedTicket.getTicketType().getId(),
                deletedTicket.getSale().getId()
        );

        return ResponseEntity.ok(deletedTicketDTO);
    }

}

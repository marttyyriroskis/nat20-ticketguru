package com.nat20.ticketguru.api;

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
public class RestTicketController {
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final SaleRepository saleRepository;

    public RestTicketController(TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository, SaleRepository saleRepository) {
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
                    ticket.getTicketType().getId(),
                    ticket.getSale().getId()))
                .collect(Collectors.toList()));
    }
    
    // Get ticket by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        return ResponseEntity.ok(new TicketDTO(
                    ticket.getBarcode(),
                    ticket.getUsedAt(),
                    ticket.getPrice(),
                    ticket.getTicketType().getId(),
                    ticket.getSale().getId()));
    }
    
    // Post a new ticket
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        TicketType ticketType = ticketTypeRepository.findById(ticketDTO.ticketTypeId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Ticket Type not found"));
        Sale sale = saleRepository.findById(ticketDTO.saleId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sale not found"));

        // TODO: Autogenerate barcode
        Ticket newTicket = new Ticket();
        newTicket.setBarcode(ticketDTO.barcode());
        newTicket.setUsedAt(ticketDTO.usedAt());
        newTicket.setPrice(ticketDTO.price());
        newTicket.setTicketType(ticketType);
        newTicket.setSale(sale);

        ticketRepository.save(newTicket);

        TicketDTO newTicketDTO = new TicketDTO(
                newTicket.getBarcode(),
                newTicket.getUsedAt(),
                newTicket.getPrice(),
                newTicket.getTicketType().getId(),
                newTicket.getSale().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(newTicketDTO);
    }

    // Edit ticket
    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        
        if (!existingTicket.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticketToUpdate = existingTicket.get();

        ticketToUpdate.setUsedAt(ticketDTO.usedAt());
        ticketToUpdate.setPrice(ticketDTO.price());

        TicketType ticketType = ticketTypeRepository.findById(ticketDTO.ticketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid TicketType ID"));
        Sale sale = saleRepository.findById(ticketDTO.saleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Sale ID"));

        ticketToUpdate.setTicketType(ticketType);
        ticketToUpdate.setSale(sale);

        Ticket updatedTicket = ticketRepository.save(ticketToUpdate);

        TicketDTO updatedTicketDTO = new TicketDTO(
                updatedTicket.getBarcode(),
                updatedTicket.getUsedAt(),
                updatedTicket.getPrice(),
                updatedTicket.getTicketType().getId(),
                updatedTicket.getSale().getId()
        );

        return ResponseEntity.ok(updatedTicketDTO);
    }

    // Delete ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (!ticketOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
        }

        ticketRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

}

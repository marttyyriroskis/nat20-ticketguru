package com.nat20.ticketguru.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
@Validated
public class RestSaleController {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public RestSaleController(SaleRepository saleRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("")
    public Iterable<Sale> getSales() {
        return saleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Sale getSale(@PathVariable("id") Long id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (!optionalSale.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found with id: " + id);
        }
        Sale sale = optionalSale.get();
        return sale;
    }

    @PostMapping("")
    public Sale createSale(@Valid @RequestBody Sale sale) {
        // check User
        if (sale.getUser() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user in request body");
        }
        Optional<User> existingUser = userRepository.findById(sale.getUser().getId());
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // check Tickets
        List<Ticket> validTickets = new ArrayList<>();
        List<Ticket> requestTickets = sale.getTickets();
        for (Ticket requestTicket : requestTickets) {
            Optional<Ticket> ticket = ticketRepository.findById(requestTicket.getId());
            if (!ticket.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket");
            }
            // TODO maybe need to add more validation here? 
            validTickets.add(ticket.get());
        }

        Sale newSale = new Sale();
        newSale.setUser(existingUser.get());
        newSale.setTickets(validTickets);
        newSale.setPaidAt(LocalDateTime.now());

        return saleRepository.save(newSale);
    }

}

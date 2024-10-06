package com.nat20.ticketguru.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
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
        Sale newSale = new Sale();
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
            // set ticket association with sale in ticket
            ticket.get().setSale(newSale);
        }
        if (validTickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid tickets in Sale");
        }

        newSale.setUser(existingUser.get());
        newSale.setTickets(validTickets);
        if (sale.getPaidAt() != null) {
            newSale.setPaidAt(sale.getPaidAt());
        }
        return saleRepository.save(newSale);
    }

    @PutMapping("/{id}")
    public Sale editSale(@Valid @RequestBody Sale editedSale, @PathVariable("id") Long id, @RequestParam Long userId) {
        // check Id
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        // check User
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No userId in query parameter");
        }
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        // check Tickets
        List<Ticket> validTickets = new ArrayList<>();
        for (Ticket requestTicket : editedSale.getTickets()) {
            Optional<Ticket> ticket = ticketRepository.findById(requestTicket.getId());
            if (!ticket.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticket");
            }
            // TODO maybe need to add more validation here? 
            validTickets.add(ticket.get());
            // set ticket association with sale in ticket
            ticket.get().setSale(sale);
        }
        sale.setUser(existingUser);
        sale.setTickets(validTickets);
        if (editedSale.getPaidAt() != null) {
            sale.setPaidAt(editedSale.getPaidAt());
        }
        return saleRepository.save(sale);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable("id") Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        // remove sale association from tickets before delete (we want to preserve the tickets in the database)
        for (Ticket ticket : sale.getTickets()) {
            ticket.setSale(null);
        }
        saleRepository.deleteById(id);
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

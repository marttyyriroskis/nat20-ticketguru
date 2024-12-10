package com.nat20.ticketguru.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.dto.BasketDTO;
import com.nat20.ticketguru.dto.SaleDTO;
import com.nat20.ticketguru.dto.TicketItemDTO;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for TicketSale
 *
 * @author Paul Carlson
 * @version 1.0
 */
@Service
public class TicketSaleService {

    private final TicketRepository ticketRepository;
    private final SaleRepository saleRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;
    private final TicketSummaryService ticketSummaryService;

    public TicketSaleService(TicketRepository ticketRepository,
            SaleRepository saleRepository,
            TicketTypeRepository ticketTypeRepository,
            UserRepository userRepository,
            TicketSummaryService ticketSummaryService) {
        this.ticketRepository = ticketRepository;
        this.saleRepository = saleRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.userRepository = userRepository;
        this.ticketSummaryService = ticketSummaryService;
    }

    /**
     * Generates tickets for the specified ticket type and quantity. Validates
     * the availability of tickets before generating and creates a list of
     * tickets with the specified attributes.
     *
     * @param ticketItemDTO the DTO containing the ticket type ID, price, and
     * quantity
     * @return a list of generated tickets
     * @throws IllegalArgumentException if the specified ticket type does not
     * exist or if the requested quantity exceeds availability
     */
    @Transactional
    public List<Ticket> generateTickets(TicketItemDTO ticketItemDTO) {
        ticketSummaryService.refreshMateralizedView();
        TicketType ticketType = ticketTypeRepository.findById(ticketItemDTO.ticketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("TicketType with ID " + ticketItemDTO.ticketTypeId() + " does not exist."));

        int totalAvailable = ticketSummaryService.countAvailableTicketsForTicketType(ticketType.getId());
        if (totalAvailable < ticketItemDTO.quantity()) {
            throw new IllegalArgumentException("Trying to generate " + ticketItemDTO.quantity()
                    + " tickets, but " + totalAvailable + " tickets are available.");
        }

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < ticketItemDTO.quantity(); i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketType(ticketType);
            ticket.setPrice(ticketItemDTO.price());
            tickets.add(ticket);
        }
        ticketSummaryService.refreshMateralizedView();
        return tickets;
    }

    /**
     * Processes a ticket sale by generating tickets for the items in the basket
     * and associating them with a sale. Saves the sale and its tickets to the
     * database and updates the materialized view for ticket availability.
     *
     * @param basketDTO the DTO containing the tickets in the sale
     * @param userId the ID of the user making the purchase
     * @return a DTO representing the completed sale
     * @throws IllegalArgumentException if any ticket type does not exist or if
     * requested ticket quantities exceed availability
     */
    @Transactional
    public SaleDTO processSale(BasketDTO basketDTO, Long userId) {
        ticketSummaryService.refreshMateralizedView();
        Sale sale = new Sale();
        sale.setUser(userRepository.findById(userId).get());

        List<Ticket> tickets = basketDTO.ticketItems().stream()
                .flatMap(ticketItemDTO -> generateTickets(ticketItemDTO).stream())
                .peek(ticket -> ticket.setSale(sale))
                .collect(Collectors.toList());

        BigDecimal total = tickets.stream()
                .map(ticket -> BigDecimal.valueOf(ticket.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        sale.setTransactionTotal(total);
        saleRepository.save(sale);
        ticketRepository.saveAll(tickets);

        ticketSummaryService.refreshMateralizedView();
        return mapToSaleDTO(sale, tickets);
    }

    /**
     * Maps a sale entity and its tickets to a SaleDTO object
     *
     * @param sale the sale entity to be mapped
     * @param tickets the list of tickets associated with the sale
     * @return a SaleDTO representing the sale and its associated tickets
     */
    public SaleDTO mapToSaleDTO(Sale sale, List<Ticket> tickets) {
        SaleDTO saleDTO = new SaleDTO(
                sale.getId(),
                sale.getPaidAt(),
                sale.getUserId(),
                sale.getTransactionTotal(),
                tickets.stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()));
        return saleDTO;
    }
}

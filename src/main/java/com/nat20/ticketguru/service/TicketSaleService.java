package com.nat20.ticketguru.service;

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

    @Transactional
    public List<Ticket> generateTickets(TicketItemDTO ticketItemDTO) {
        ticketSummaryService.refreshMateralizedView();
        TicketType ticketType = ticketTypeRepository.findById(ticketItemDTO.ticketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("TicketType with ID " + ticketItemDTO.ticketTypeId() + " does not exist."));

        // check availability
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

    @Transactional
    public SaleDTO processSale(BasketDTO basketDTO, Long userId) {
        ticketSummaryService.refreshMateralizedView();
        Sale sale = new Sale();
        sale.setUser(userRepository.findById(userId).get());

        // TODO: check ticket availability
        List<Ticket> tickets = basketDTO.ticketItems().stream()
                .flatMap(ticketItemDTO -> generateTickets(ticketItemDTO).stream())
                .peek(ticket -> ticket.setSale(sale))
                .collect(Collectors.toList());

        saleRepository.save(sale);
        ticketRepository.saveAll(tickets);

        // update material view for ticket availability
        ticketSummaryService.refreshMateralizedView();
        return mapToSaleDTO(sale, tickets);
    }

    public SaleDTO mapToSaleDTO(Sale sale, List<Ticket> tickets) {
        SaleDTO saleDTO = new SaleDTO(
                sale.getId(),
                sale.getPaidAt(),
                sale.getUserId(),
                tickets.stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()));
        return saleDTO;
    }
}

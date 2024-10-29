package com.nat20.ticketguru.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.dto.BasketDTO;
import com.nat20.ticketguru.dto.SaleDTO;
import com.nat20.ticketguru.dto.TicketItemDTO;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.UserRepository;

@Service
public class TicketSaleService {

    private final TicketRepository ticketRepository;
    private final SaleRepository saleRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;

    public TicketSaleService(TicketRepository ticketRepository,
            SaleRepository saleRepository,
            TicketTypeRepository ticketTypeRepository,
            UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.saleRepository = saleRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.userRepository = userRepository;
    }

    public List<Ticket> generateTickets(TicketItemDTO ticketItemDTO) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < ticketItemDTO.quantity(); i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketType(ticketTypeRepository.findById(ticketItemDTO.ticketTypeId()).get());
            ticket.setPrice(ticketItemDTO.price());
            tickets.add(ticket);
        }
        return tickets;
    }

    public SaleDTO processSale(BasketDTO basketDTO, Long userId) {
        Sale sale = new Sale();
        sale.setUser(userRepository.findById(userId).get());
        saleRepository.save(sale);

        List<Ticket> tickets = basketDTO.ticketItems().stream()
                .flatMap(ticketItemDTO -> generateTickets(ticketItemDTO).stream())
                .peek(ticket -> ticket.setSale(sale))
                .collect(Collectors.toList());

        ticketRepository.saveAll(tickets);
        return mapToSaleDTO(sale, tickets);
    }

    public SaleDTO mapToSaleDTO(Sale sale, List<Ticket> tickets) {
        SaleDTO saleDTO = new SaleDTO(sale.getId(),
                sale.getPaidAt(),
                sale.getUserId(),
                tickets.stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()));
        return saleDTO;
    }
}

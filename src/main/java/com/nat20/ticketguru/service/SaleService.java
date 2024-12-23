package com.nat20.ticketguru.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.UserRepository;

/**
 * Searches for sales based on the provided filters: date range and user ID.
 * Combines different filtering criteria to retrieve sales that match the specified conditions.
 *
 * @param start the start of the date range, or null for no start date
 * @param end the end of the date range, or null for no end date
 * @param userId the ID of the user, or null for no user
 * @return a list of sales matching the specified criteria
 * @throws IllegalArgumentException if User is not found
 */
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    public SaleService(SaleRepository saleRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
    }

    public List<Sale> searchSales(LocalDateTime start, LocalDateTime end, Long userId) {

        Optional<User> user = (userId != null) ? userRepository.findById(userId) : Optional.empty();

        if (userId != null && user.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        if (start != null && end != null && userId != null) {
            return saleRepository.findByPaidAtBetweenAndUser(start, end, user.get());
        } else if (start != null && end != null) {
            return saleRepository.findByPaidAtBetween(start, end);
        } else if (start != null && userId != null) {
            return saleRepository.findByPaidAtAfterAndUser(start, user.get());
        } else if (end != null && userId != null) {
            return saleRepository.findByPaidAtBeforeAndUser(end, user.get());
        } else if (start != null) {
            return saleRepository.findByPaidAtAfter(start);
        } else if (end != null) {
            return saleRepository.findByPaidAtBefore(end);
        } else if (userId != null) {
            return saleRepository.findByUser(user.get());
        } else {
            // can be disallowed in the controller
            return (List<Sale>) saleRepository.findAll();
        }
    }

}

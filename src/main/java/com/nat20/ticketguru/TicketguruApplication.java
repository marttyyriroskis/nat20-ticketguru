package com.nat20.ticketguru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.domain.RoleRepository;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.TicketRepository;
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.domain.UserRepository;

@SpringBootApplication
public class TicketguruApplication {

    private static final Logger log = LoggerFactory.getLogger(TicketguruApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TicketguruApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(TicketRepository ticketRepository, UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {
            log.info("Creating a few ticket test entries");
            ticketRepository.save(new Ticket());
            ticketRepository.save(new Ticket());

            log.info("Creating a few role test entries");
            roleRepository.save(new Role("cashier"));
            roleRepository.save(new Role("event organizer"));

            log.info("Creating a few user test entries");
            userRepository.save(new User("test1@test.com", "User1", "Cashier", "VerySecureHash1", roleRepository.findByTitle("cashier").get()));
            userRepository.save(new User("test2@test.com", "User2", "Event Organizer", "VerySecureHash2", roleRepository.findByTitle("event organizer").get()));

        };
    }

}

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
import com.nat20.ticketguru.domain.Zipcode;
import com.nat20.ticketguru.domain.ZipcodeRepository;

@SpringBootApplication
public class TicketguruApplication {

    private static final Logger log = LoggerFactory.getLogger(TicketguruApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TicketguruApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(TicketRepository ticketRepository, UserRepository userRepository, RoleRepository roleRepository, ZipcodeRepository zipcodeRepository) {
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

            log.info("Creating a few zipcode test entries");
            zipcodeRepository.save(new Zipcode("00100", "Helsinki"));
            zipcodeRepository.save(new Zipcode("00200", "Helsinki"));
            zipcodeRepository.save(new Zipcode("00300", "Helsinki"));
            zipcodeRepository.save(new Zipcode("00500", "Helsinki"));
            zipcodeRepository.save(new Zipcode("02100", "Espoo"));
            zipcodeRepository.save(new Zipcode("02200", "Espoo"));
            zipcodeRepository.save(new Zipcode("02600", "Espoo"));
            zipcodeRepository.save(new Zipcode("33100", "Tampere"));
            zipcodeRepository.save(new Zipcode("33200", "Tampere"));
            zipcodeRepository.save(new Zipcode("40100", "Jyväskylä"));
            zipcodeRepository.save(new Zipcode("40200", "Jyväskylä"));
            zipcodeRepository.save(new Zipcode("70100", "Kuopio"));
            zipcodeRepository.save(new Zipcode("70200", "Kuopio"));
            zipcodeRepository.save(new Zipcode("90100", "Oulu"));
            zipcodeRepository.save(new Zipcode("90200", "Oulu"));
            zipcodeRepository.save(new Zipcode("33100", "Tampere"));
            zipcodeRepository.save(new Zipcode("33800", "Tampere"));
            zipcodeRepository.save(new Zipcode("00140", "Helsinki"));
            zipcodeRepository.save(new Zipcode("04200", "Kerava"));
            zipcodeRepository.save(new Zipcode("01510", "Vantaa"));
            zipcodeRepository.save(new Zipcode("01600", "Vantaa"));
            zipcodeRepository.save(new Zipcode("28100", "Pori"));
            zipcodeRepository.save(new Zipcode("60100", "Seinäjoki"));
            zipcodeRepository.save(new Zipcode("80100", "Joensuu"));
            zipcodeRepository.save(new Zipcode("90140", "Oulu"));

        };
    }

}

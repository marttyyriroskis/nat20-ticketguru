package com.nat20.ticketguru;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nat20.ticketguru.domain.Event;
import com.nat20.ticketguru.domain.Permission;
import com.nat20.ticketguru.domain.Role;
import com.nat20.ticketguru.domain.Sale;
import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.TicketType;
import com.nat20.ticketguru.domain.User;
import com.nat20.ticketguru.domain.Venue;
import com.nat20.ticketguru.domain.Zipcode;
import com.nat20.ticketguru.repository.EventRepository;
import com.nat20.ticketguru.repository.PermissionRepository;
import com.nat20.ticketguru.repository.RoleRepository;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.UserRepository;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.repository.ZipcodeRepository;

@SpringBootApplication
public class TicketguruApplication {

    private static final Logger log = LoggerFactory.getLogger(TicketguruApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TicketguruApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PermissionRepository permissionRepository, TicketRepository ticketRepository,
            UserRepository userRepository, RoleRepository roleRepository, ZipcodeRepository zipcodeRepository,
            SaleRepository saleRepository, EventRepository eventRepository, VenueRepository venueRepository,
            TicketTypeRepository ticketTypeRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String[] args) throws Exception {

                log.info("Creating a few role test entries");
                roleRepository.save(new Role("cashier"));
                roleRepository.save(new Role("event organizer"));

                log.info("Creating a few permission test entries");
                permissionRepository.save(new Permission("read"));
                permissionRepository.save(new Permission("write"));

                log.info("Creating a few user test entries");
                userRepository.save(new User("test1@test.com", "User1", "Cashier", "VerySecureHash1",
                        roleRepository.findByTitle("cashier").get()));
                userRepository.save(new User("test2@test.com", "User2", "Event Organizer", "VerySecureHash2",
                        roleRepository.findByTitle("event organizer").get()));

                log.info("Creating a few zipcode test entries");
                zipcodeRepository.save(new Zipcode("00100", "Helsinki"));
                zipcodeRepository.save(new Zipcode("00200", "Helsinki"));
                zipcodeRepository.save(new Zipcode("00250", "Helsinki"));
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

                log.info("Creating a few venue test entries");
                venueRepository.save(new Venue("Bunkkeri", "Bunkkeritie 1", zipcodeRepository.findById("00100").get()));
                venueRepository.save(new Venue("Helsingin jäähalli", "Nordenskiöldinkatu 11-13",
                        zipcodeRepository.findById("00250").get()));
                venueRepository.save(new Venue("National Museum", "Museokatu 1", zipcodeRepository.findByZipcode("00100")));

                log.info("Creating a few event test entries");
                eventRepository.save(new Event("Death metal karaoke", "Öriöriöriöriörirprir!!!!!", 10,
                        LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                        LocalDateTime.of(2055, 10, 12, 12, 00), venueRepository.findById(1L).get()));
                eventRepository.save(new Event("Disney On Ice", "Mikki-hiiret jäällä. Suih suih vaan!", 10000,
                        LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                        LocalDateTime.of(2055, 10, 12, 12, 00), venueRepository.findById(2L).get()));
                eventRepository.save(new Event("A Night at the Museum", "Night-show at the National Museum", 500,
                        LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                        LocalDateTime.of(2055, 10, 12, 12, 00), venueRepository.findById(1L).get()));

                log.info("Creating a few ticket type test entries");
                ticketTypeRepository.save(new TicketType("adult", 29.99, null, eventRepository.findById(1L).get()));
                ticketTypeRepository.save(new TicketType("student", 14.99, null, eventRepository.findById(1L).get()));
                ticketTypeRepository.save(new TicketType("pensioner", 14.99, null, eventRepository.findById(1L).get()));
                ticketTypeRepository.save(new TicketType("vip", 79.99, 20, eventRepository.findById(1L).get()));

                log.info("Creating a few ticket test entries");
                Sale sale1 = new Sale(LocalDateTime.now(), new ArrayList<>(), userRepository.findById(1L).get());
                Sale sale2 = new Sale(LocalDateTime.now(), new ArrayList<>(), userRepository.findById(2L).get());
                saleRepository.save(sale1);
                saleRepository.save(sale2);
                
                ticketRepository.save(new Ticket(null, 10.0, ticketTypeRepository.findById(1L).get(), saleRepository.findById(2L).get()));
                ticketRepository.save(new Ticket(null, 20.0, ticketTypeRepository.findById(2L).get(), saleRepository.findById(1L).get()));
                ticketRepository.save(new Ticket(null, 30.0, ticketTypeRepository.findById(3L).get(), saleRepository.findById(2L).get()));
                ticketRepository.save(new Ticket(null, 40.0, ticketTypeRepository.findById(4L).get(), saleRepository.findById(1L).get()));

                log.info("Creating a few sale test entries");
                Ticket ticket1 = ticketRepository.findById(1L).get();
                Ticket ticket2 = ticketRepository.findById(2L).get();
                Ticket ticket3 = ticketRepository.findById(3L).get();

                ticket1.setTicketType(ticketTypeRepository.findById(1L).get());
                ticket2.setTicketType(ticketTypeRepository.findById(2L).get());
                ticket3.setTicketType(ticketTypeRepository.findById(2L).get());

                ticket1.setSale(sale1);
                ticket2.setSale(sale1);
                ticket3.setSale(sale2);

                ticket1 = ticketRepository.save(ticket1);
                ticket2 = ticketRepository.save(ticket2);
                ticket3 = ticketRepository.save(ticket3);

                sale1.getTickets().add(ticket1);
                sale1.getTickets().add(ticket2);
                sale2.getTickets().add(ticket3);

                saleRepository.save(sale1);
                saleRepository.save(sale2);
                Sale savedSale1 = saleRepository.findById(sale1.getId()).get();
                Sale savedSale2 = saleRepository.findById(sale2.getId()).get();
                log.info("\n\nSaved Sale1:\n" + savedSale1.toString());
                log.info("\nTickets in Sale1:\n" + savedSale2.getTickets().toString());
                log.info("\n\nSales:\n");
                log.info(savedSale2.toString());
                log.info("\n\n\n");
            }
        };
    }

}

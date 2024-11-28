package com.nat20.ticketguru;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

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
import com.nat20.ticketguru.repository.RoleRepository;
import com.nat20.ticketguru.repository.SaleRepository;
import com.nat20.ticketguru.repository.TicketRepository;
import com.nat20.ticketguru.repository.TicketTypeRepository;
import com.nat20.ticketguru.repository.UserRepository;
import com.nat20.ticketguru.repository.VenueRepository;
import com.nat20.ticketguru.repository.ZipcodeRepository;

@Component
@Profile("test") // only runs in the 'test' profile
public class TestDataInitializer implements CommandLineRunner {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ZipcodeRepository zipcodeRepository;
    private final SaleRepository saleRepository;
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TicketTypeRepository ticketTypeRepository;

    public TestDataInitializer(TicketRepository ticketRepository,
            UserRepository userRepository, RoleRepository roleRepository, ZipcodeRepository zipcodeRepository,
            SaleRepository saleRepository, EventRepository eventRepository, VenueRepository venueRepository,
            TicketTypeRepository ticketTypeRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.zipcodeRepository = zipcodeRepository;
        this.saleRepository = saleRepository;
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.ticketTypeRepository = ticketTypeRepository;

    }

    @Override
    public void run(String... args) {

        System.out.println("Initializing test data ...");

        Role ticketInspectorRole = roleRepository.save(new Role("TICKET_INSPECTOR"));
        Role salespersonRole = roleRepository.save(new Role("SALESPERSON"));
        Role coordinatorRole = roleRepository.save(new Role("COORDINATOR"));
        Role adminRole = roleRepository.save(new Role("ADMIN"));

        ticketInspectorRole.addPermissions(
                Permission.VIEW_TICKETS,
                Permission.USE_TICKETS);
        salespersonRole.addPermissions(
                Permission.VIEW_EVENTS,
                Permission.VIEW_SALES,
                Permission.CREATE_SALES,
                Permission.EDIT_SALES,
                Permission.CONFIRM_SALES,
                Permission.VIEW_TICKETS,
                Permission.DELETE_TICKETS,
                Permission.USE_TICKETS,
                Permission.VIEW_TICKET_TYPES,
                Permission.VIEW_VENUES);
        coordinatorRole.addPermissions(
                Permission.VIEW_EVENTS,
                Permission.CREATE_EVENTS,
                Permission.EDIT_EVENTS,
                Permission.VIEW_TICKET_TYPES,
                Permission.CREATE_TICKET_TYPES,
                Permission.EDIT_TICKET_TYPES,
                Permission.DELETE_TICKET_TYPES,
                Permission.VIEW_VENUES,
                Permission.CREATE_VENUES,
                Permission.EDIT_VENUES,
                Permission.DELETE_VENUES
        );
        // Admin has all existing permissions defined in Permission
        adminRole.addPermissions(Permission.values());
        roleRepository.save(ticketInspectorRole);
        roleRepository.save(salespersonRole);
        roleRepository.save(coordinatorRole);
        roleRepository.save(adminRole);

        String salespersonPass = BCrypt.hashpw("salesperson", BCrypt.gensalt());
        String adminPass = BCrypt.hashpw("admin", BCrypt.gensalt());
        String coordinatorPass = BCrypt.hashpw("coordinator", BCrypt.gensalt());
        // salesperson@test.com "salesperson", admin@test.com "admin", "coordinator@test.com" "coordinator"
        userRepository.save(new User("salesperson@test.com", "salesperson1", "Cashier", salespersonPass,
                roleRepository.findByTitle("SALESPERSON").get()));
        userRepository.save(new User("admin@test.com", "Admin1", "Site Admin", adminPass,
                roleRepository.findByTitle("ADMIN").get()));
        userRepository.save(new User("coordinator@test.com", "Coordinator1", "Event Coordinator", coordinatorPass,
                roleRepository.findByTitle("COORDINATOR").get()));

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

        venueRepository.save(new Venue("Bunkkeri", "Bunkkeritie 1", zipcodeRepository.findByZipcode("00100")));
        venueRepository.save(new Venue("Helsingin jäähalli", "Nordenskiöldinkatu 11-13", zipcodeRepository.findByZipcode("00250")));
        venueRepository.save(new Venue("National Museum", "Museokatu 1", zipcodeRepository.findByZipcode("00100")));

        eventRepository.save(new Event("Death metal karaoke", "Öriöriöriöriörirprir!!!!!", 10,
                LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                venueRepository.findById(1L).get()));
        eventRepository.save(new Event("Disney On Ice", "Mikki-hiiret jäällä. Suih suih vaan!", 10000,
                LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                venueRepository.findById(2L).get()));
        eventRepository.save(new Event("A Night at the Museum", "Night-show at the National Museum", 500,
                LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                venueRepository.findById(1L).get()));

        ticketTypeRepository.save(new TicketType("adult", 29.99, null, eventRepository.findById(1L).get()));
                    ticketTypeRepository.save(new TicketType("student", 14.99, null, eventRepository.findById(1L).get()));
                    ticketTypeRepository.save(new TicketType("pensioner", 14.99, null, eventRepository.findById(1L).get()));
                    ticketTypeRepository.save(new TicketType("vip", 79.99, 20, eventRepository.findById(1L).get()));

        Sale sale1 = new Sale(LocalDateTime.now(), new ArrayList<>(), userRepository.findById(1L).get());
        Sale sale2 = new Sale(LocalDateTime.now(), new ArrayList<>(), userRepository.findById(2L).get());
        saleRepository.save(sale1);
        saleRepository.save(sale2);

        ticketRepository.save(new Ticket(10.0, ticketTypeRepository.findById(1L).get(), saleRepository.findById(2L).get()));
        ticketRepository.save(new Ticket(20.0, ticketTypeRepository.findById(2L).get(), saleRepository.findById(1L).get()));
        ticketRepository.save(new Ticket(30.0, ticketTypeRepository.findById(3L).get(), saleRepository.findById(2L).get()));
        ticketRepository.save(new Ticket(40.0, ticketTypeRepository.findById(4L).get(), saleRepository.findById(1L).get()));

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
        saleRepository.findById(sale1.getId()).get();
        saleRepository.findById(sale2.getId()).get();

        System.out.println("Test data initialized.");
    }

}

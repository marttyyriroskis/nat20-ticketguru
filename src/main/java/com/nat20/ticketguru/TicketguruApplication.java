package com.nat20.ticketguru;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;

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

@SpringBootApplication
public class TicketguruApplication {

    private static final Logger log = LoggerFactory.getLogger(TicketguruApplication.class);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Helsinki"));

        SpringApplication.run(TicketguruApplication.class, args);
    }

    @Bean
    @Profile("!test") // do not run when 'test' profile is active
    public CommandLineRunner demo(TicketRepository ticketRepository,
            UserRepository userRepository, RoleRepository roleRepository, ZipcodeRepository zipcodeRepository,
            SaleRepository saleRepository, EventRepository eventRepository, VenueRepository venueRepository,
            TicketTypeRepository ticketTypeRepository, @Value("${app.skipPrompt:false}") boolean skipPrompt,
            JdbcTemplate jdbcTemplate) {

        return (String[] args) -> {
            if (skipPrompt) {
                log.info("Ticket Guru is now up and running 👌");
                return;
            }

            System.out.println("Would you like to reset and re-initialize the database with some sample data? (yes/no)");

            try (Scanner scanner = new Scanner(System.in)) {
                if (scanner.nextLine().equals("yes")) {

                    log.info("Resetting the database...");
                    String resetSQL = """
                        DO $$ 
                        DECLARE
                        table_name text;
                        BEGIN
                        FOR table_name IN
                                SELECT tablename
                                FROM pg_tables
                                WHERE schemaname = 'public'
                        LOOP
                                EXECUTE 'TRUNCATE TABLE ' || quote_ident(table_name) || ' RESTART IDENTITY CASCADE;';
                        END LOOP;
                        END $$;
                                """;

                    jdbcTemplate.execute(resetSQL);

                    // drop materialized view
                    jdbcTemplate.execute("DROP MATERIALIZED VIEW IF EXISTS event_ticket_summary;");

                    System.out.println("Database reset complete.");

                    log.info("Creating a few role test entries");
                    Role ticketInspectorRole = roleRepository.save(new Role("TICKET_INSPECTOR"));
                    Role salespersonRole = roleRepository.save(new Role("SALESPERSON"));
                    Role coordinatorRole = roleRepository.save(new Role("COORDINATOR"));
                    Role adminRole = roleRepository.save(new Role("ADMIN"));

                    log.info("Add a few permissions to roles");
                    ticketInspectorRole.addPermissions(
                            Permission.VIEW_TICKETS,
                            Permission.USE_TICKETS,
                            Permission.VIEW_EVENTS,
                            Permission.VIEW_VENUES,
                            Permission.VIEW_TICKET_TYPES);
                    salespersonRole.addPermissions(
                            Permission.VIEW_EVENTS,
                            Permission.VIEW_SALES,
                            Permission.CREATE_SALES,
                            Permission.EDIT_SALES,
                            Permission.CONFIRM_SALES,
                            Permission.VIEW_TICKETS,
                            Permission.DELETE_TICKETS,
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

                    log.info("Creating a few user test entries");
                    String salespersonPass = BCrypt.hashpw("salesperson", BCrypt.gensalt());
                    String adminPass = BCrypt.hashpw("admin", BCrypt.gensalt());
                    String coordinatorPass = BCrypt.hashpw("coordinator", BCrypt.gensalt());
                    String inspectorPass = BCrypt.hashpw("inspector", BCrypt.gensalt());
                    // salesperson@test.com "salesperson", admin@test.com "admin", "coordinator@test.com" "coordinator"
                    userRepository.save(new User("salesperson@test.com", "salesperson1", "Cashier", salespersonPass,
                            roleRepository.findByTitle("SALESPERSON").get()));
                    userRepository.save(new User("admin@test.com", "Admin1", "Site Admin", adminPass,
                            roleRepository.findByTitle("ADMIN").get()));
                    userRepository.save(new User("coordinator@test.com", "Coordinator1", "Event Coordinator", coordinatorPass,
                            roleRepository.findByTitle("COORDINATOR").get()));
                        userRepository.save(new User("inspector@test.com", "Inspector1", "Ticket Inspector", inspectorPass,
                            roleRepository.findByTitle("TICKET_INSPECTOR").get()));

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
                    venueRepository.save(new Venue("Bunkkeri", "Bunkkeritie 1", zipcodeRepository.findByZipcode("00100")));
                    venueRepository.save(new Venue("Helsingin jäähalli", "Nordenskiöldinkatu 11-13", zipcodeRepository.findByZipcode("00250")));
                    venueRepository.save(new Venue("National Museum", "Museokatu 1", zipcodeRepository.findByZipcode("00100")));

                    log.info("Creating a few event test entries");
                    eventRepository.save(new Event("Death metal karaoke", "Öriöriöriöriörirprir!!!!!", 100,
                            LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                            venueRepository.findById(1L).get()));
                    eventRepository.save(new Event("Disney On Ice", "Mikki-hiiret jäällä. Suih suih vaan!", 10000,
                            LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                            venueRepository.findById(2L).get()));
                    eventRepository.save(new Event("A Night at the Museum", "Night-show at the National Museum", 500,
                            LocalDateTime.of(2055, 10, 12, 12, 00), LocalDateTime.of(2055, 10, 12, 12, 00),
                            venueRepository.findById(1L).get()));

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

                    ticketRepository.save(new Ticket(10.0, ticketTypeRepository.findById(1L).get(), saleRepository.findById(2L).get()));
                    ticketRepository.save(new Ticket(20.0, ticketTypeRepository.findById(2L).get(), saleRepository.findById(1L).get()));
                    ticketRepository.save(new Ticket(30.0, ticketTypeRepository.findById(3L).get(), saleRepository.findById(2L).get()));
                    ticketRepository.save(new Ticket(40.0, ticketTypeRepository.findById(4L).get(), saleRepository.findById(1L).get()));

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

                    // create materialized view for ticket availability using SQL statements
                    String createMaterializedViewSQL = """
                        CREATE MATERIALIZED VIEW IF NOT EXISTS event_ticket_summary AS  
                        SELECT
                                e.id AS event_id,
                                tt.id AS ticket_type_id,
                                count(t.id) FILTER (WHERE t.sale_id IS NOT NULL AND t.deleted_at IS NULL AND t.price != 0) AS tickets_sold,
                                count(t.id) FILTER (WHERE t.deleted_at IS NULL) AS tickets_total,
                                sum(t.price) FILTER (WHERE t.sale_id IS NOT NULL AND t.deleted_at IS NULL)::numeric(10,2) AS total_revenue
                        FROM
                                events e
                        JOIN
                                ticket_types tt ON tt.event_id = e.id AND tt.deleted_at IS NULL
                        LEFT JOIN
                                tickets t ON t.ticket_type_id = tt.id
                        WHERE
                                e.deleted_at IS NULL
                        GROUP BY 
                                e.id, tt.id
                        WITH DATA;
                                        """;

                    String createIndex = "CREATE UNIQUE INDEX idx_ticket_summary ON event_ticket_summary (ticket_type_id);";

                    jdbcTemplate.execute(createMaterializedViewSQL);
                    jdbcTemplate.execute(createIndex);
                    jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY event_ticket_summary;");
                }

                System.out.println("Ticket Guru is now up and running. 👌");
            }
        };
    }

}

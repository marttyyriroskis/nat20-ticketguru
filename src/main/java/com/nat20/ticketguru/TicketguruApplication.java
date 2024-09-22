package com.nat20.ticketguru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.nat20.ticketguru.domain.Ticket;
import com.nat20.ticketguru.domain.TicketRepository;

@SpringBootApplication
public class TicketguruApplication {

	private static final Logger log = LoggerFactory.getLogger(TicketguruApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TicketguruApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TicketRepository ticketRepository) {
		return (args) -> {
			log.info("Creating a few test entries");
			ticketRepository.save(new Ticket());
			ticketRepository.save(new Ticket());
		};
	}

}

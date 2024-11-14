package com.nat20.ticketguru;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CustomCorsConfig {

    @Bean
    public CorsConfigurationSource getCorsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5500", "http://127.0.0.1:5500", "https://ticket-client.hellmanstudios.fi")); // TODO: Specify allowed origin(s)
        config.setAllowedMethods(List.of("GET", "PUT", "POST", "OPTIONS")); // Allow specified HTTP methods
        config.setAllowedHeaders(List.of("*")); // Allow all headers
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config); // Specify endpoint(s)

        return source;

    }

}

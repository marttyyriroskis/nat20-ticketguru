package com.nat20.ticketguru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.nat20.ticketguru.service.UserDetailServiceImpl;

/**
 * Configuration class for WebSecurity
 * 
 * @author Julia Hämäläinen
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

    private final UserDetailServiceImpl userDetailsService;
    private final CustomCorsConfig customCorsConfig;

    public WebSecurityConfig(UserDetailServiceImpl userDetailsService, CustomCorsConfig customCorsConfig) {
        this.userDetailsService = userDetailsService;
        this.customCorsConfig = customCorsConfig;
    }

    /**
     * Configures the security settings for the application.
     * This method sets up CORS, CSRF protection, authorization rules, HTTP headers, 
     * form login, and logout configurations.
     * 
     * @param http the HttpSecurity instance for configuring web security
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security
     */
    private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
        new AntPathRequestMatcher("/h2-console"),
        new AntPathRequestMatcher("/index"),
        new AntPathRequestMatcher("/layout")};

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(customCorsConfig.getCorsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // TODO: disable csrf for now, enable later for production?
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(antMatcher("/css/**")).permitAll()
                                .requestMatchers(WHITE_LIST_URLS).permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .headers(
                        headers -> headers
                                .frameOptions(frameOptions -> frameOptions
                                .disable())
                )
                .formLogin(formlogin -> 
                    formlogin
                        //.loginPage("/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler()) // redirect to page the user tried to access before login
                        .permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();

    }

    /**
     * Configures the global authentication manager with a custom user details service 
     * and password encoder.
     * This method sets up the authentication manager to use the specified 
     * UserDetailsService and a BCrypt password encoder for password hashing and verification.
     * 
     * @param auth the AuthenticationManagerBuilder instance for configuring authentication
     * @throws Exception if an error occurs while configuring the authentication manager
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

}

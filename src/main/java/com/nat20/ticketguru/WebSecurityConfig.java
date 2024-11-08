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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

}

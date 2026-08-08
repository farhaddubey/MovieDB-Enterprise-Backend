package com.backend.movie_db.MovieDB_Enterprise_Backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**")
                                .permitAll()
                                .requestMatchers("/api/admin")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                );
        return httpSecurity.build();
    }
}
// /api/auth/register -> PERMIT ALL
// /api/auth/login -> PERMIT ALL
// /api/movies -> JWT REQUIRED
// /api/watchlist -> JWT REQUIRED
// /api/admin -> ROLE_ADMIN required

// Why Bcrypt ?
// SHA -> FAST -> BAD
// BCRYPT -> SLOW -> ADAPTIVE -> SALTED (MUCH SAFER

// SecurityConfig.java
// Responsibilities
// Password Encoder
// Authentication Manager
// Protected APIs
// Public APIs
// JWT Filter

// SECURITY FLOW
// REQUEST -> JWT FILTER -> VALIDATE TOKEN -> AUTHENTICATION CONTEXT -> CONTROLLER

// REQUEST -> JWT-AUTHENTICATION-FILTER -> USERNAME-PASSWORD-AUTHENTICATION-FILTER -> CONTROLLER



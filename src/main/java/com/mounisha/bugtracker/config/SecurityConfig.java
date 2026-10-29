package com.mounisha.bugtracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(
            JwtFilter jwtFilter
    ) {

        this.jwtFilter = jwtFilter;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(cors -> {})

                // Authorization
                .authorizeHttpRequests(auth -> auth

                        // Allow OPTIONS requests
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // Auth APIs
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/uploads/**"
                        ).permitAll()

                        // TEMPORARY PUBLIC BUG APIs
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bugs/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/bugs/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/bugs/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/bugs/**"
                        ).permitAll()

                                // Only admins can create or delete users
                                .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

// Logged-in users can view users
                                .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                         //Projects
                        .requestMatchers(
                                "/api/projects/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/comments/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/comments/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/comments/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/comments/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/notifications/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/activities/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/email/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/dashboard/**"
                        ).permitAll()
                        // Other APIs
                        .anyRequest()
                        .authenticated()
                )

                // Stateless JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // JWT Filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();

    }
}
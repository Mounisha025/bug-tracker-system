package com.mounisha.bugtracker.config;

import com.mounisha.bugtracker.service.CustomUserDetailsService;
import com.mounisha.bugtracker.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.
        SecurityContextHolder;

import org.springframework.security.core.userdetails.
        UserDetails;

import org.springframework.security.web.authentication.
        WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService
            userDetailsService;

    public JwtFilter(

            JwtService jwtService,

            CustomUserDetailsService
                    userDetailsService

    ) {

        this.jwtService =
                jwtService;

        this.userDetailsService =
                userDetailsService;
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    )

            throws ServletException,
            IOException {

        final String authHeader =

                request.getHeader(
                        "Authorization"
                );

        String jwt = null;

        String email = null;

        // CHECK AUTH HEADER
        if (authHeader != null

                && authHeader.startsWith(
                "Bearer "
        )) {

            jwt =
                    authHeader.substring(7);

            email =
                    jwtService.extractEmail(jwt);
        }

        // AUTHENTICATE USER
        if (email != null

                && SecurityContextHolder
                .getContext()
                .getAuthentication()
                == null) {

            UserDetails userDetails =

                    userDetailsService
                            .loadUserByUsername(
                                    email
                            );

            if (jwtService.validateToken(

                    jwt,

                    userDetails.getUsername()

            )) {

                UsernamePasswordAuthenticationToken
                        authToken =

                        new UsernamePasswordAuthenticationToken(

                                userDetails,

                                null,

                                userDetails.getAuthorities()
                        );

                authToken.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {

        String path =
                request.getServletPath();

        return path.contains("/api/auth")

                // TEMPORARY PUBLIC BUG APIs
                || path.contains("/api/bugs")

                //|| path.contains("/api/users")

                || path.contains("/api/projects")

                || path.contains("/api/comments")

                || path.contains("/api/activities")

                || path.contains("/api/email")

                || path.contains("/api/dashboard")

                || path.contains("/api/notifications")

                || path.contains("/v3/api-docs")

                || path.contains("/swagger-ui")

                || path.contains("/swagger-resources")

                || path.contains("/webjars");





    }
}
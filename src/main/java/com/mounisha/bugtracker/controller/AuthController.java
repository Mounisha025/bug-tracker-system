package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.dto.LoginRequest;
import com.mounisha.bugtracker.service.JwtService;
import com.mounisha.bugtracker.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request) {

        var user = userService
                .getUserByEmail(request.getEmail());

        if (user.isPresent() &&
                passwordEncoder.matches(
                        request.getPassword(),
                        user.get().getPassword())) {

            return jwtService.generateToken(
                    user.get().getEmail(),
                    user.get().getRole().name()
            );
        }

        return "Invalid email or password";
    }
}
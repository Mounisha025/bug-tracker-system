
package com.mounisha.bugtracker.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.mounisha.bugtracker.dto.LoginRequest;
import com.mounisha.bugtracker.dto.LoginResponse;
import com.mounisha.bugtracker.entity.User;
import com.mounisha.bugtracker.service.GoogleAuthService;
import com.mounisha.bugtracker.service.JwtService;
import com.mounisha.bugtracker.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthService googleAuthService;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            GoogleAuthService googleAuthService) {

        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.googleAuthService = googleAuthService;
    }

    // Existing email/password login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        var user = userService.getUserByEmail(request.getEmail());

        if (user.isPresent()
                && passwordEncoder.matches(
                request.getPassword(),
                user.get().getPassword())) {

            var existingUser = user.get();

            String token = jwtService.generateToken(
                    existingUser.getEmail(),
                    existingUser.getRole().name()
            );

            return ResponseEntity.ok(
                    new LoginResponse(
                            existingUser.getId(),
                            existingUser.getName(),
                            existingUser.getEmail(),
                            existingUser.getRole().name(),
                            token
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
    }

    // Google login for existing users only
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(
            @RequestBody Map<String, String> request) {

        try {
            String credential = request.get("credential");

            if (credential == null || credential.isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Google credential is required");
            }

            GoogleIdToken.Payload payload =
                    googleAuthService.verifyToken(credential);

            String email = payload.getEmail();

            var existingUser = userService.getUserByEmail(email);

            if (existingUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("No existing account found. Contact your administrator.");
            }

            User user = existingUser.get();

            String token = jwtService.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(
                    new LoginResponse(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole().name(),
                            token
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Google sign-in failed. Please try again.");
        }
    }
}

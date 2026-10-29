package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(
            EmailService emailService
    ) {

        this.emailService = emailService;

    }

    @PostMapping("/test")
    public String sendTestEmail() {

        emailService.sendEmail(

                "YOUR_REAL_EMAIL@gmail.com",

                "Bug Tracker Test",

                "Congratulations! Your Email Service is working."

        );

        return "Email Sent Successfully";

    }

}
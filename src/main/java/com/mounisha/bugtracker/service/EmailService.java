
package com.mounisha.bugtracker.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Send a general email
    public void sendEmail(String to, String subject, String body) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            System.out.println("Attempting to send email to: " + to);

            mailSender.send(message);

            System.out.println(
                    "Email accepted by mail service for: " + to
            );

        } catch (Exception e) {
            System.err.println(
                    "Email sending failed for: " + to
            );
            System.err.println("Reason: " + e.getMessage());
            throw e;
        }
    }

    // Email when a bug is assigned
    public void sendBugAssignedEmail(
            String to,
            String bugTitle,
            String priority,
            String status
    ) {

        String subject = "New Bug Assigned";

        String body =
                "Hello,\n\n"
                        + "A new bug has been assigned to you.\n\n"
                        + "Title: " + bugTitle + "\n"
                        + "Priority: " + priority + "\n"
                        + "Status: " + status + "\n\n"
                        + "Please login to Bug Tracker for more details.\n\n"
                        + "Regards,\n"
                        + "Bug Tracker Team";

        sendEmail(to, subject, body);
    }

    // Email when bug status changes
    public void sendStatusChangedEmail(
            String to,
            String bugTitle,
            String oldStatus,
            String newStatus
    ) {

        String subject = "Bug Status Updated";

        String body =
                "Hello,\n\n"
                        + "The status of your reported bug has changed.\n\n"
                        + "Bug Title: " + bugTitle + "\n"
                        + "Old Status: " + oldStatus + "\n"
                        + "New Status: " + newStatus + "\n\n"
                        + "Please login to Bug Tracker for more details.\n\n"
                        + "Regards,\n"
                        + "Bug Tracker Team";

        sendEmail(to, subject, body);
    }
}

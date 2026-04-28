package com.company.dashboard.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.company.dashboard.service.EmailService;

@Service
public class MailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendBaseUrl;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // ================= INITIAL ONBOARDING MAIL =================
    @Override
    public void sendOnboardingMail(String to, String name, String token) {
        String link = frontendBaseUrl + "/onboarding?token=" + token;

        String subject = "Complete Your Onboarding – Immediate Action Required";
        String body = "Hi " + name + ",\n\n" +
                "Your onboarding process has been initiated.\n\n" +
                "Click the link below to submit your onboarding details:\n" +
                link + "\n\n" +
                "Important:\n" +
                "- This link can be used only once.\n" +
                "- Do not share this link with anyone.\n\n" +
                "Thank you,\nHR Department";

        sendMail(to, subject, body);
    }

    // ================= REJECTED DOCUMENT MAIL =================
    @Override
    public void sendRejectedOnboardingMail(String to, String name, String token, String rejectedFields) {
        String link = frontendBaseUrl + "/onboarding?token=" + token;

        String subject = "Document Rejection – Action Required";
        String body = "Hi " + name + ",\n\n" +
                "The following documents were rejected:\n\n" +
                rejectedFields + "\n" +
                "Please re-upload only the above documents via the link below:\n" +
                link + "\n\n" +
                "Thank you,\nHR Department";

        sendMail(to, subject, body);
    }

    // ================= APPROVAL MAIL =================
    @Override
    public void sendApprovalMail(String to, String name) {
        String subject = "Onboarding Approved Successfully";
        String body = "Hi " + name + ",\n\n" +
                "Your onboarding documents have been reviewed and approved successfully.\n\n" +
                "Welcome aboard! We are excited to have you with us.\n\n" +
                "Thank you,\nHR Department";

        sendMail(to, subject, body);
    }
}
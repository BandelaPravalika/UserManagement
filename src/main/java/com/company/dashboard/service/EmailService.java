package com.company.dashboard.service;

public interface EmailService {

    void sendMail(String to, String subject, String body);

    // Initial onboarding mail
    void sendOnboardingMail(String to, String name, String token);

    // Mail when HR rejects any document
    void sendRejectedOnboardingMail(String to, String name, String token, String rejectedFields);

    // Mail when onboarding is approved
    void sendApprovalMail(String to, String name);
}
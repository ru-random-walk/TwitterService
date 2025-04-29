package ru.randomwalk.twitterservice.service;

public interface EmailService {
    void sendEmail(String email, String subject, String body, boolean isHtml);
}

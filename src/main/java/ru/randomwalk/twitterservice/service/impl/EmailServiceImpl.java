package ru.randomwalk.twitterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.randomwalk.twitterservice.service.EmailService;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmail(String email, String subject, String body, boolean isHtml) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            log.info("Sending message with subject {} to {}", subject, email);
            mailSender.send(message);
            log.info("Message is sent");
        } catch (Exception e) {
            log.error("Error sending message with subject {}", subject, e);
            throw new RuntimeException(e);
        }
    }
}

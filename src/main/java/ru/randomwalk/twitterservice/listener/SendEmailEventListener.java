package ru.randomwalk.twitterservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.random.walk.dto.SendEmailEvent;
import ru.random.walk.topic.EventTopic;
import ru.randomwalk.twitterservice.service.EmailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendEmailEventListener {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @KafkaListener(topics = EventTopic.SEND_EMAIL)
    public void handleSendEmailEvent(String message) {
        try {
            log.info("Processing send email event");
            var event = objectMapper.readValue(message, SendEmailEvent.class);
            emailService.sendEmail(event.recipient(), event.subject(), event.body(), event.isHtml());
        } catch (Exception e) {
            log.error("Error processing send email event {}", message, e);
        }
    }
}

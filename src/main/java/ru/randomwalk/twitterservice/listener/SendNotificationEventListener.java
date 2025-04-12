package ru.randomwalk.twitterservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;
import ru.random.walk.dto.SendNotificationEvent;
import ru.random.walk.topic.EventTopic;
import ru.randomwalk.twitterservice.service.NotificationSendingService;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendNotificationEventListener {

    private final NotificationSendingService notificationSendingService;
    private final ObjectMapper objectMapper;

    @RetryableTopic
    @KafkaListener(topics = EventTopic.SEND_NOTIFICATION)
    public void processSendNotificationEvent(String message) {
        try {
            log.info("Got event {} to send as notification", message);
            var event = objectMapper.readValue(message, SendNotificationEvent.class);
            notificationSendingService.sendNotification(event);
        } catch (Exception e) {
            log.error("Error sending notification {}", message, e);
            throw new RuntimeException(e);
        }
    }
}

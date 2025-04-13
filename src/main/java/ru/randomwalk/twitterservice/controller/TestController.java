package ru.randomwalk.twitterservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.random.walk.dto.SendNotificationEvent;
import ru.randomwalk.twitterservice.service.NotificationSendingService;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('TESTER') or hasAuthority('TWITTER_API_CLIENT_SCOPE')")
@Profile({"test", "local"})
public class TestController {

    private final NotificationSendingService notificationSendingService;

    @PostMapping("/send-notification")
    @Operation(description = "Test endpoint for notification sending")
    public void sendNotification(SendNotificationEvent event) {
        try {
            notificationSendingService.sendNotification(event);
        } catch (Exception e) {
            log.error("Exception sending notification {}", event, e);
            throw new RuntimeException(e);
        }
    }
}

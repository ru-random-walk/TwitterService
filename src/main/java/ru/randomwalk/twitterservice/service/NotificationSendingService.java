package ru.randomwalk.twitterservice.service;

import ru.random.walk.dto.SendNotificationEvent;

public interface NotificationSendingService {
    void sendNotification(SendNotificationEvent event);
}

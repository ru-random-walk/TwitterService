package ru.randomwalk.twitterservice.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import ru.random.walk.dto.SendNotificationEvent;

public interface NotificationSendingService {
    void sendNotification(SendNotificationEvent event) throws FirebaseMessagingException;
}

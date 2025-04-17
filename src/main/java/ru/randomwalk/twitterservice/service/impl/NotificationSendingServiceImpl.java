package ru.randomwalk.twitterservice.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.random.walk.dto.SendNotificationEvent;
import ru.randomwalk.twitterservice.model.entity.Device;
import ru.randomwalk.twitterservice.service.NotificationSendingService;
import ru.randomwalk.twitterservice.service.DeviceService;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationSendingServiceImpl implements NotificationSendingService {

    private final DeviceService deviceService;

    private static final Set<MessagingErrorCode> EXPIRED_TOKEN_ERROR_CODES = EnumSet.of(
            MessagingErrorCode.INVALID_ARGUMENT,
            MessagingErrorCode.UNREGISTERED
    );

    @Override
    public void sendNotification(SendNotificationEvent event) {
        Notification notification = Notification.builder()
                .setTitle(event.title())
                .setBody(event.body())
                .build();
        List<Device> devices = deviceService.getDevicesByUserId(event.userId());

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var device : devices) {
                executor.submit(() -> sendMessage(notification, device, event.additionalData()));
            }
        }
    }

    private void sendMessage(Notification notification, Device device, Map<String, String> additionalData) {
        try {
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(device.getDeviceToken())
                    .putAllData(additionalData)
                    .build();
            String messageId = FirebaseMessaging.getInstance().send(message);
            log.info("Message {} were sent with id {}", message, messageId);
        } catch (FirebaseMessagingException firebaseException) {
            log.warn("Error sending notification {}", notification, firebaseException);
            expireToken(firebaseException, device);
        }
    }

    private void expireToken(FirebaseMessagingException exception, Device device) {
        var errorCode = exception.getMessagingErrorCode();
        if (EXPIRED_TOKEN_ERROR_CODES.contains(errorCode)) {
            log.info("Expiring token {} with error code {}", device.getDeviceToken(), errorCode);
            deviceService.deleteById(device.getId());
        } else {
            log.error("Error during firebase message sending for device {}", device.getDeviceToken(), exception);
            throw new RuntimeException(exception);
        }
    }
}

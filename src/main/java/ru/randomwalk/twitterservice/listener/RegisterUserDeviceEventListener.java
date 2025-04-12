package ru.randomwalk.twitterservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.random.walk.dto.AddDeviceTokenEvent;
import ru.random.walk.topic.EventTopic;
import ru.randomwalk.twitterservice.service.DeviceService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterUserDeviceEventListener {

    private final ObjectMapper objectMapper;
    private final DeviceService deviceService;

    @KafkaListener(topics = EventTopic.ADD_DEVICE)
    public void registerDevice(String message) {
        try {
            log.info("Got event to register user's device");
            var event = objectMapper.readValue(message, AddDeviceTokenEvent.class);
            deviceService.addNewDeviceToken(event.userId(), event.deviceToken());
        } catch (Exception e) {
            log.error("Error adding device", e);
        }
    }
}

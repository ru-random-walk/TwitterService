package ru.randomwalk.twitterservice.service;

import ru.randomwalk.twitterservice.model.entity.Device;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DeviceService {
    List<Device> getDevicesByUserId(UUID userId);

    void deleteById(UUID id);

    void addNewDeviceToken(UUID userId, String deviceToken);

    void refreshExistingToken(UUID userId, String previousToken, String newToken);
}

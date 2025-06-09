package ru.randomwalk.twitterservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.randomwalk.twitterservice.AbstractContainerTest;
import ru.randomwalk.twitterservice.model.entity.Device;
import ru.randomwalk.twitterservice.repository.DeviceRepository;
import ru.randomwalk.twitterservice.repository.UserAccountRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("local")
class DeviceServiceImplTest extends AbstractContainerTest {

    @Autowired
    private DeviceServiceImpl deviceService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional
    @Rollback
    @Test
    void getDevicesByUserId() {
        String deviceToken = "some device token";
        UUID userId = UUID.randomUUID();
        deviceService.addNewDeviceToken(userId, deviceToken);

        List<Device> devices = deviceService.getDevicesByUserId(userId);

        assertEquals(1, devices.size());
    }

    @Transactional
    @Rollback
    @Test
    void deleteById() {
        String deviceToken = "some device token";
        UUID userId = UUID.randomUUID();
        deviceService.addNewDeviceToken(userId, deviceToken);
        var device = deviceRepository.findByUser_UserIdAndDeviceToken(userId, deviceToken).get();

        deviceService.deleteById(device.getId());

        assertFalse(deviceRepository.existsByUser_UserIdAndDeviceToken(userId, deviceToken));
    }

    @Transactional
    @Rollback
    @Test
    void addNewDeviceToken() {
        String deviceToken = "some device token";
        UUID userId = UUID.randomUUID();

        deviceService.addNewDeviceToken(userId, deviceToken);

        var device = deviceRepository.findByUser_UserIdAndDeviceToken(userId, deviceToken);
        var user = userAccountRepository.findById(userId);
        assertTrue(device.isPresent());
        assertTrue(user.isPresent());
        assertEquals(user.get().getUserId(), device.get().getUser().getUserId());
    }

    @Test
    void refreshExistingToken() {
        String deviceToken = "some device token";
        UUID userId = UUID.randomUUID();
        String newToken = "new token";
        deviceService.addNewDeviceToken(userId, deviceToken);
        var deviceId = deviceRepository.findByUser_UserIdAndDeviceToken(userId, deviceToken).get().getId();

        deviceService.refreshExistingToken(userId, deviceToken, newToken);

        var device = deviceRepository.findById(deviceId).get();
        assertEquals(newToken, device.getDeviceToken());
    }

    @Test
    void createNewTokenIfNothingToRefresh() {
        String deviceToken = "some device token";
        UUID userId = UUID.randomUUID();
        String newToken = "new token";

        deviceService.refreshExistingToken(userId, deviceToken, newToken);

        var device = deviceRepository.findByUser_UserIdAndDeviceToken(userId, newToken);
        assertTrue(device.isPresent());
        assertEquals(userId, device.get().getUser().getUserId());
    }
}
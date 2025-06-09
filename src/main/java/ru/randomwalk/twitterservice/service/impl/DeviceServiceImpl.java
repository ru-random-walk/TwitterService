package ru.randomwalk.twitterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomwalk.twitterservice.model.entity.Device;
import ru.randomwalk.twitterservice.model.entity.UserAccount;
import ru.randomwalk.twitterservice.model.exception.NotFoundException;
import ru.randomwalk.twitterservice.repository.DeviceRepository;
import ru.randomwalk.twitterservice.repository.UserAccountRepository;
import ru.randomwalk.twitterservice.service.DeviceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public List<Device> getDevicesByUserId(UUID userId) {
        return deviceRepository.findAllByUser_UserId(userId);
    }

    @Override
    public void deleteById(UUID id) {
        deviceRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void addNewDeviceToken(UUID userId, String deviceToken) {
        log.info("Adding new device token for user {}", userId);
        var user = userAccountRepository.findById(userId)
                .orElseGet(() -> createNewUser(userId));

        if (deviceRepository.existsByUser_UserIdAndDeviceToken(userId, deviceToken)) {
            log.warn("Device with provided token already exists");
            return;
        }

        var device = createDevice(user, deviceToken);
        log.info("Device with id {} were added for user {}", device.getId(), userId);
    }

    @Override
    public void refreshExistingToken(UUID userId, String previousToken, String newToken) {
        log.info("Refreshing existing token for user {}", userId);
        var optionalDevice = deviceRepository.findByUser_UserIdAndDeviceToken(userId, previousToken);
        if (optionalDevice.isPresent()) {
            var device = optionalDevice.get();
            device.setDeviceToken(newToken);
            deviceRepository.save(device);
            log.info("Token was refreshed for device {}", device.getId());
        } else {
            addNewDeviceToken(userId, newToken);
        }
    }

    private Device createDevice(UserAccount user, String deviceToken) {
        var device = new Device();
        device.setDeviceToken(deviceToken);
        device.setCreatedAt(LocalDateTime.now());
        device.setUser(user);
        return deviceRepository.save(device);
    }

    private UserAccount createNewUser(UUID userId) {
        log.info("Registering new user {}", userId);
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userId);
        return userAccountRepository.save(userAccount);
    }
}

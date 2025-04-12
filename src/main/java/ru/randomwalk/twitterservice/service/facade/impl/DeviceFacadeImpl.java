package ru.randomwalk.twitterservice.service.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.randomwalk.twitterservice.model.dto.request.AddDeviceTokenRequest;
import ru.randomwalk.twitterservice.model.dto.request.RefreshDeviceTokenRequest;
import ru.randomwalk.twitterservice.service.DeviceService;
import ru.randomwalk.twitterservice.service.facade.DeviceFacade;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceFacadeImpl implements DeviceFacade {

    private final DeviceService deviceService;

    @Override
    public void addToken(String userId, AddDeviceTokenRequest request) {
        UUID user = UUID.fromString(userId);
        deviceService.addNewDeviceToken(user, request.token());
    }

    @Override
    public void refreshToken(String userId, RefreshDeviceTokenRequest request) {
        UUID user = UUID.fromString(userId);
        deviceService.refreshExistingToken(user, request.previousToken(), request.newToken());
    }
}

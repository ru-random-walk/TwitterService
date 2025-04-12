package ru.randomwalk.twitterservice.service.facade;

import ru.randomwalk.twitterservice.model.dto.request.AddDeviceTokenRequest;
import ru.randomwalk.twitterservice.model.dto.request.RefreshDeviceTokenRequest;

public interface DeviceFacade {
    void addToken(String userId, AddDeviceTokenRequest request);

    void refreshToken(String userId, RefreshDeviceTokenRequest request);
}

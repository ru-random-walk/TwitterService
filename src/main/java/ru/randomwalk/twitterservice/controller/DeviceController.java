package ru.randomwalk.twitterservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.randomwalk.twitterservice.model.dto.request.AddDeviceTokenRequest;
import ru.randomwalk.twitterservice.model.dto.request.RefreshDeviceTokenRequest;
import ru.randomwalk.twitterservice.service.facade.DeviceFacade;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/device")
public class DeviceController {

    private final DeviceFacade deviceFacade;

    @PostMapping("/add")
    public void addDeviceToken(@RequestBody AddDeviceTokenRequest request, Principal principal) {
        deviceFacade.addToken(principal.getName(), request);
    }

    @PutMapping("/refresh")
    public void refreshToken(@RequestBody RefreshDeviceTokenRequest request, Principal principal) {
        deviceFacade.refreshToken(principal.getName(), request);
    }
}

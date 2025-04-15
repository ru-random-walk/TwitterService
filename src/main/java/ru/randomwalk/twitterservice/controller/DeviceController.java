package ru.randomwalk.twitterservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAuthority('DEFAULT_USER')")
public class DeviceController {

    private final DeviceFacade deviceFacade;

    @PostMapping("/add")
    @Operation(description = "Add device token for current authorised user")
    public void addDeviceToken(@RequestBody AddDeviceTokenRequest request, Principal principal) {
        deviceFacade.addToken(principal.getName(), request);
    }

    @PutMapping("/refresh")
    @Operation(description = "Refresh existing device token for current authorised user")
    public void refreshToken(@RequestBody RefreshDeviceTokenRequest request, Principal principal) {
        deviceFacade.refreshToken(principal.getName(), request);
    }
}

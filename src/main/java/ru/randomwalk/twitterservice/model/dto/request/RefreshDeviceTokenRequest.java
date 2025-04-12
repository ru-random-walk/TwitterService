package ru.randomwalk.twitterservice.model.dto.request;

public record RefreshDeviceTokenRequest(
        String previousToken,
        String newToken
) {

}

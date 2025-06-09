package ru.randomwalk.twitterservice.model.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record ApiErrorDto (
        String message
) {}

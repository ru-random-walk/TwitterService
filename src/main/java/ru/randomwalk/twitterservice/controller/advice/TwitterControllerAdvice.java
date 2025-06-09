package ru.randomwalk.twitterservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.randomwalk.twitterservice.model.dto.ApiErrorDto;
import ru.randomwalk.twitterservice.model.exception.NotFoundException;

@RestControllerAdvice
@Slf4j
public class TwitterControllerAdvice {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorDto> exceptionHandler(NotFoundException e) {
        log.warn("Handle not found exception", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDto(e.getMessage()));
    }
}

package me.andregarcia0412.pipeline.shared.exception.dto;

import org.springframework.http.HttpStatus;

public record ErrorMessage(
        String message,
        HttpStatus httpStatus,
        String field
) {
    public ErrorMessage(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }
}

package me.andregarcia0412.pipeline.shared.exception.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

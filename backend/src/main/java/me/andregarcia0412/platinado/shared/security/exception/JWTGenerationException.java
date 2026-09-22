package me.andregarcia0412.pipeline.shared.security.exception;

public class JWTGenerationException extends RuntimeException {
    public JWTGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

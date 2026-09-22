package me.andregarcia0412.pipeline.shared.exception.exceptions;

public class ConflictException extends RuntimeException {
    private final String field;

    public ConflictException(String message) {
        this(message, null);
    }

    public ConflictException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

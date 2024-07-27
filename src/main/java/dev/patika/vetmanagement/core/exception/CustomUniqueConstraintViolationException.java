package dev.patika.vetmanagement.core.exception;

public class CustomUniqueConstraintViolationException extends RuntimeException {
    public CustomUniqueConstraintViolationException(String message) {
        super(message);
    }
}

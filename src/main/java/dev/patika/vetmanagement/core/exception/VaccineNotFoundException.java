package dev.patika.vetmanagement.core.exception;

public class VaccineNotFoundException extends RuntimeException {
    public VaccineNotFoundException(String message) {
        super(message);
    }
}

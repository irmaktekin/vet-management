package dev.patika.vetmanagement.core.exception;

    public class AvailableDateNotFoundException extends RuntimeException {
        public AvailableDateNotFoundException(String message) {
            super(message);
        }
    }


package dev.patika.vetmanagement.core.exception;

public class DuplicateRecordException extends RuntimeException {
    private final String fieldName;

    public DuplicateRecordException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
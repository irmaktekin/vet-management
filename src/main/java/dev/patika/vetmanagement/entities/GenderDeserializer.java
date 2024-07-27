package dev.patika.vetmanagement.entities;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.patika.vetmanagement.core.exception.InvalidGenderException;

import java.io.IOException;

public class GenderDeserializer extends JsonDeserializer<Gender> {

    @Override
    public Gender deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String value = p.getText().toUpperCase(); // Convert to uppercase
        try {
            return Gender.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidGenderException("Invalid gender value: " + p.getText());
        }
    }


}
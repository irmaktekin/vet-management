package dev.patika.vetmanagement.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GenderDeserializer.class)

public enum Gender {
    MALE,
    FEMALE,
    OTHER
}
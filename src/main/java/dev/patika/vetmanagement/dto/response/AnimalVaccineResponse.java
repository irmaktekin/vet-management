package dev.patika.vetmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalVaccineResponse {
    private String animalName;
    private String vaccineName;
    private LocalDate protectionFinishDate;
    private String species;
    private String colour;
}

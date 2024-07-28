package dev.patika.vetmanagement.dto.response.animal;

import dev.patika.vetmanagement.entities.Gender;
import dev.patika.vetmanagement.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Gender gender;
    private String colour;
    private LocalDate dateOfBirth;
    private Set<Vaccine> vaccines = new HashSet<>();
    private Long customerId;
}

package dev.patika.vetmanagement.dto.request.animal;

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
public class AnimalSaveRequest {
    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;
    private Set<Vaccine> vaccines = new HashSet<>();
    private Long customerId;
    private Long doctorId;
}

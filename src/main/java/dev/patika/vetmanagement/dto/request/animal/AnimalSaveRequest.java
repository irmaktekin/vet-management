package dev.patika.vetmanagement.dto.request.animal;

import dev.patika.vetmanagement.entities.Gender;
import dev.patika.vetmanagement.entities.Vaccine;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnimalSaveRequest {
    private String name;

    private String species;

    private String breed;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String colour;

    private LocalDate dateOfBirth;
    private Set<Long> vaccines = new HashSet<>();
    private Long customerId;
}

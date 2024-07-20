package dev.patika.vetmanagement.dto.request.animal;

import dev.patika.vetmanagement.entities.Vaccine;
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

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;
    private Set<Long> vaccines = new HashSet<>();
    private Long customerId;
    private Long doctorId;
}

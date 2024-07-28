package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.dto.response.AnimalVaccineResponse;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AnimalVaccine;
import dev.patika.vetmanagement.entities.Doctor;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IAnimalVaccineService {
    AnimalVaccine addVaccineToAnimal(Long animalId, Long vaccineId);
    Page<AnimalVaccine> getAnimalVaccinesByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate,Pageable pageable);
    void validateAndAddVaccines(Set<Vaccine> newVaccines);

}

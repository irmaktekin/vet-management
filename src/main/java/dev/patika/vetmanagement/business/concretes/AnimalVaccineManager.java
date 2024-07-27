package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAnimalVaccineService;
import dev.patika.vetmanagement.core.exception.AnimalNotFoundException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.dao.AnimalRepo;
import dev.patika.vetmanagement.dao.AnimalVaccineRepository;
import dev.patika.vetmanagement.dao.VaccineRepo;
import dev.patika.vetmanagement.dto.response.AnimalVaccineResponse;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AnimalVaccine;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalVaccineManager implements IAnimalVaccineService {
    private final AnimalVaccineRepository animalVaccineRepository;
    private final VaccineRepo vaccineRepository;

    private final AnimalRepo animalRepository;

    public AnimalVaccineManager(AnimalVaccineRepository animalVaccineRepository, VaccineRepo vaccineRepository, AnimalRepo animalRepository) {
        this.animalVaccineRepository = animalVaccineRepository;

        this.vaccineRepository = vaccineRepository;
        this.animalRepository = animalRepository;
    }

    public AnimalVaccine addVaccineToAnimal(Long animalId,Long vaccineId) {
        // Hayvanı ve aşıyı bul
        System.out.println(animalId);
        Animal animal = animalRepository.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new NotFoundException("Animal not found"));
        Vaccine vaccine = vaccineRepository.findVaccinesByCodeAndName(vaccineId)
                .orElseThrow(() -> new NotFoundException("Vaccine not found"));

        LocalDate currentDate = LocalDate.now();
        boolean isVaccineActive = animalVaccineRepository.findActiveAnimalVaccinesByVaccineId(animalId, vaccineId)
                .stream()
                .anyMatch(av -> av.getVaccine().getProtectionFinishDate().isAfter(currentDate));
       if (isVaccineActive) {
            throw new NotFoundException("The animal already has an active vaccine with this code and name.");
        }
        System.out.println("test2");
        // Aşıyı ekle
        AnimalVaccine animalVaccine = new AnimalVaccine(animal, vaccine);

        animalVaccineRepository.save(animalVaccine);
        return animalVaccine;


    }

    @Override
    public Page<AnimalVaccine> getAnimalVaccinesByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate,Pageable pageable) {
         return animalVaccineRepository.findAnimalVaccinesByProtectionFinishDateBetween(startDate, endDate,pageable);
    }


}

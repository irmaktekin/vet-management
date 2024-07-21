package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAnimalVaccineService;
import dev.patika.vetmanagement.dao.AnimalRepo;
import dev.patika.vetmanagement.dao.AnimalVaccineRepository;
import dev.patika.vetmanagement.dao.VaccineRepo;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AnimalVaccine;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.Vaccine;
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

    public AnimalVaccine addVaccineToAnimal(Long animalId, String vaccineCode, String vaccineName) {
        // Hayvanı ve aşıyı bul
        Animal animal = animalRepository.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        Vaccine vaccine = vaccineRepository.findVaccinesByCodeAndName(vaccineCode, vaccineName)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        System.out.println("tets2");

        LocalDate currentDate = LocalDate.now();
        boolean isVaccineActive = animalVaccineRepository.findActiveAnimalVaccinesByVaccineId(animalId, vaccineCode, vaccineName)
                .stream()
                .anyMatch(av -> av.getVaccine().getProtectionFinishDate().isAfter(currentDate));


        if (!isVaccineActive) {
            throw new IllegalStateException("The animal already has an active vaccine with this code and name.");
        }
        System.out.println("test2");
        // Aşıyı ekle
        AnimalVaccine animalVaccine = new AnimalVaccine(animal, vaccine);
        System.out.println(animalVaccine.getAnimal().getColour());

        animalVaccineRepository.save(animalVaccine);
        return animalVaccine;


    }
}

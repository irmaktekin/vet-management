package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAnimalVaccineService;
import dev.patika.vetmanagement.core.exception.AnimalNotFoundException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.core.exception.ValidationException;
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
import java.util.*;

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
        Animal animal = animalRepository.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new NotFoundException("Animal not found"));
        Vaccine vaccine = vaccineRepository.findVaccinesByCodeAndName(vaccineId)
                .orElseThrow(() -> new NotFoundException("Vaccine not found"));

        String vaccineCode = vaccine.getCode();
        String vaccineName = vaccine.getName();

        LocalDate currentDate = LocalDate.now();
        boolean isVaccineActive = animalVaccineRepository.findActiveAnimalVaccinesByVaccineId(animalId, vaccineCode, vaccineName)
                .stream()
                .anyMatch(av -> av.getVaccine().getProtectionFinishDate().isAfter(currentDate));
       if (isVaccineActive) {
            throw new ValidationException("The animal already has an active vaccine with this code and name.");
        }
        AnimalVaccine animalVaccine = new AnimalVaccine(animal, vaccine);

        animalVaccineRepository.save(animalVaccine);
        return animalVaccine;


    }



    @Override
    public Page<AnimalVaccine> getAnimalVaccinesByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate,Pageable pageable) {
         return animalVaccineRepository.findAnimalVaccinesByProtectionFinishDateBetween(startDate, endDate,pageable);
    }

    @Override
    public void validateAndAddVaccines(Set<Vaccine> newVaccines) {
        // Use a map to keep track of vaccines by their name and code
        // Create a map to keep track of vaccines by their name and code
        // Create a map to track vaccines by name and code
        Map<String, List<Vaccine>> vaccineMap = new HashMap<>();

        // Populate the map with new vaccines
        for (Vaccine vaccine : newVaccines) {
            String key = vaccine.getName() + ":" + vaccine.getCode();
            vaccineMap.computeIfAbsent(key, k -> new ArrayList<>()).add(vaccine);
        }

        // Check for duplicates with active protection
        for (List<Vaccine> vaccinesWithSameNameAndCode : vaccineMap.values()) {
            if (vaccinesWithSameNameAndCode.size() > 1) {
                // Extract name and code
                String name = vaccinesWithSameNameAndCode.get(0).getName();
                String code = vaccinesWithSameNameAndCode.get(0).getCode();

                // Check if any active vaccines exist with the same name and code
                List<Vaccine> activeVaccines = animalVaccineRepository.findActiveVaccinesByNameAndCode(name, code);

                if (!activeVaccines.isEmpty()) {
                    // If any active vaccine with the same name and code is found, throw an exception
                    throw new ValidationException("Cannot add vaccines with name " + name + " and code " + code + " because a duplicate with active protection exists.");
                }
            }
        }
    }
    }



package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAnimalService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.dao.AnimalRepo;
import dev.patika.vetmanagement.dao.AnimalVaccineRepository;
import dev.patika.vetmanagement.dao.VaccineRepo;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AnimalVaccine;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalManager implements IAnimalService {
    private final IModelMapperService modelMapperService;
    private final AnimalRepo animalRepo;
    private final AnimalVaccineRepository animalVaccineRepository;
    private final VaccineRepo vaccineRepo;

    public AnimalManager(IModelMapperService modelMapperService, AnimalRepo animalRepo, AnimalVaccineRepository animalVaccineRepository, VaccineRepo vaccineRepo) {
        this.modelMapperService = modelMapperService;
        this.animalRepo = animalRepo;
        this.animalVaccineRepository = animalVaccineRepository;
        this.vaccineRepo = vaccineRepo;

    }


    @Override
    public Animal save(Animal animal) {
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable);
    }

    @Override
    public Animal get(long id) {
        return this.animalRepo.findById((int)id).orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public List<Animal> findByName(String name) {
        return this.animalRepo.findByName(name);
    }

    @Override
    public List<Animal> findByCustomerName(String customerName) {
        return this.animalRepo.findByCustomerName(customerName);
    }
    public void addVaccineToAnimal(Long animalId, String vaccineCode, String vaccineName) {
        LocalDate currentDate = LocalDate.now();

        // Aşıyı ID'sine göre bul
        Vaccine vaccine = vaccineRepo.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new RuntimeException("Vaccine not found with the provided ID"));

        // Hayvanı bul
        Animal animal = animalRepo.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new RuntimeException("Animal not found with the provided ID"));

        // Aşıyı kontrol et
        List<AnimalVaccine> existingVaccines = animalVaccineRepository.findActiveAnimalVaccinesByVaccineId(animalId, vaccineCode, vaccineName);

        if (existingVaccines.isEmpty()) {
            AnimalVaccine animalVaccine = new AnimalVaccine();
            animalVaccine.setAnimal(animal);
            animalVaccine.setVaccine(vaccine);

            animalVaccineRepository.save(animalVaccine);
        } else {
            throw new IllegalStateException("The vaccine already exists for the animal and is still active.");
        }
    }
}

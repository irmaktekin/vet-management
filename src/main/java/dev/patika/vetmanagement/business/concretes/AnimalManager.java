package dev.patika.vetmanagement.business.concretes;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import dev.patika.vetmanagement.business.abstracts.IAnimalService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.exception.CustomerNotFoundException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.dao.*;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AnimalVaccine;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements IAnimalService {
    private final IModelMapperService modelMapperService;
    private final AnimalRepo animalRepo;
    private final AnimalVaccineRepository animalVaccineRepository;
    private final VaccineRepo vaccineRepo;
    private final CustomerRepo customerRepo;
    private final DoctorRepo doctorRepo;

    public AnimalManager(IModelMapperService modelMapperService, AnimalRepo animalRepo, AnimalVaccineRepository animalVaccineRepository, VaccineRepo vaccineRepo, CustomerRepo customerRepo, DoctorRepo doctorRepo) {
        this.modelMapperService = modelMapperService;
        this.animalRepo = animalRepo;
        this.animalVaccineRepository = animalVaccineRepository;
        this.vaccineRepo = vaccineRepo;

        this.customerRepo = customerRepo;
        this.doctorRepo = doctorRepo;
    }


    @Override
    public Animal save(Animal animal) {


        if (animal.getCustomer() != null && animal.getCustomer().getId() != null) {
            customerRepo.findById(Math.toIntExact(animal.getCustomer().getId()))
                    .orElseThrow(() -> new NotFoundException("Customer with ID " + animal.getCustomer().getId() + " does not exist."));
        }

        // Validate Doctor
        if (animal.getDoctor() != null && animal.getDoctor().getId() != null) {
            doctorRepo.findById(Math.toIntExact(animal.getDoctor().getId()))
                    .orElseThrow(() -> new NotFoundException("Doctor with ID " + animal.getDoctor().getId() + " does not exist."));
        }




        return this.animalRepo.save(animal);
    }

    @Override
    public Animal update(Animal animal) {
        for (Vaccine vaccine : animal.getVaccines()) {
            Long vaccineId = vaccine.getId();
            if (!vaccineRepo.existsById(Math.toIntExact(vaccineId))) {
                throw new NotFoundException("Vaccine with ID " + vaccineId + " does not exist");
            }
        }

        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Animal> animalPage = this.animalRepo.findAll(pageable);
        if (animalPage.isEmpty()) {
            throw new NotFoundException("No animals found");
        }

        return animalPage;
    }

    @Override
    public Animal get(long id) {

        return this.animalRepo.findById((int)id).orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        if (!animalRepo.existsById(id)) {
            throw new NotFoundException("Animal with ID " + id + " does not exist.");
        }

        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public List<Animal> findByName(String name) {
        List<Animal> animals = animalRepo.findByName(name);
        if (animals.isEmpty()) {
            throw new NotFoundException("Animal with name " + name + " does not exist.");
        }
        return this.animalRepo.findByName(name);
    }

    @Override
    public List<Animal> findByCustomerName(String customerName) {
        List<Animal> animals = animalRepo.findByCustomerName(customerName);
        if (animals.isEmpty()) {
            throw new NotFoundException("No animals found for customer with name " + customerName);
        }
        return this.animalRepo.findByCustomerName(customerName);
    }


    public void addVaccineToAnimal(Long animalId, String vaccineCode, String vaccineName) {
        LocalDate currentDate = LocalDate.now();

        // Aşıyı ID'sine göre bul
        Vaccine vaccine = vaccineRepo.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new NotFoundException("Vaccine not found with the provided ID"));

        // Hayvanı bul
        Animal animal = animalRepo.findById(Math.toIntExact(animalId))
                .orElseThrow(() -> new NotFoundException("Animal not found with the provided ID"));

        // Aşıyı kontrol et
        Optional<AnimalVaccine> existingVaccines = animalVaccineRepository.findActiveAnimalVaccinesByVaccineId(animalId, vaccineCode,vaccineName);

        if (existingVaccines.isEmpty()) {
            AnimalVaccine animalVaccine = new AnimalVaccine();
            animalVaccine.setAnimal(animal);
            animalVaccine.setVaccine(vaccine);

            animalVaccineRepository.save(animalVaccine);
        } else {
            throw new NotFoundException("The vaccine already exists for the animal and is still active.");
        }
    }
    public boolean existsById(Long animalId) {
        return animalRepo.existsById(Math.toIntExact(animalId));
    }

    @Override
    public List<Animal> findAnimalByCustomerId(Long customerId) {
        return animalRepo.findByCustomerId(customerId);

    }
}

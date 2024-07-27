package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IVaccineService;
import dev.patika.vetmanagement.core.exception.DuplicateRecordException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.core.exception.VaccineNotFoundException;
import dev.patika.vetmanagement.dao.AnimalRepo;
import dev.patika.vetmanagement.dao.VaccineRepo;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IVaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;

    public IVaccineManager(VaccineRepo vaccineRepo, AnimalRepo animalRepo) {
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {
        //check if the vaccine with same name and code exist in the system
        if (canAddVaccine(vaccine.getId())) {
            return vaccineRepo.save(vaccine);
        } else {
            throw new DuplicateRecordException(vaccine.getCode(),"Cannot add vaccine. A vaccine with the same code and name already exists.");
        }

    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Vaccine> vaccinePage = this.vaccineRepo.findAll(pageable);
        if (vaccinePage.isEmpty()) {
            throw new NotFoundException("No vaccine found");
        }
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        if(!vaccineRepo.existsById(Math.toIntExact(vaccine.getId()))) {
            throw new NotFoundException("Vaccine with ID " + vaccine.getId() + " does not exist");
        }
        this.get(vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(long id) {
        return this.vaccineRepo.findById((int)id).orElseThrow(() -> new NotFoundException("Vaccine with ID " + id + " does not exist"));
    }

    @Override
    public boolean delete(int id) {
        if (!vaccineRepo.existsById(id)) {
            throw new NotFoundException("Vaccine with ID " + id + " does not exist.");
        }
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }

    @Override
    public List<Vaccine> findByAnimalId(int id) {
        if (!animalRepo.existsById(id)) {
            throw new NotFoundException("Animal with ID " + id + " does not exist.");
        }
        return this.vaccineRepo.findByAnimalId(id);
    }

    @Override
    public boolean existsById(Long vaccineId) {
        return vaccineRepo.existsById(Math.toIntExact(vaccineId));

    }

    public boolean canAddVaccine(Long vaccineId) {
        Optional<Vaccine> activeVaccines = vaccineRepo.findVaccinesByCodeAndName(vaccineId);
        return activeVaccines.isEmpty();
    }
}

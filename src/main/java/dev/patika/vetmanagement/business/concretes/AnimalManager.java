package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAnimalService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.dao.AnimalRepo;
import dev.patika.vetmanagement.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager implements IAnimalService {
    private final IModelMapperService modelMapperService;
    private final AnimalRepo animalRepo;

    public AnimalManager(IModelMapperService modelMapperService, AnimalRepo animalRepo) {
        this.modelMapperService = modelMapperService;
        this.animalRepo = animalRepo;
    }


    @Override
    public Animal save(Animal animal) {
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
}

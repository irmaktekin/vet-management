package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {
    Animal save(Animal animal);
    Animal update(Animal animal);
    Page<Animal> cursor (int page, int pageSize);
    Animal get(long id);
    boolean delete(int id);
    List<Animal> findByName(String name);
    List<Animal> findByCustomerName(String customerName);
    boolean existsById(Long animalId);
    List<Animal> findAnimalByCustomerId(Long customerId);
    Optional<Animal> findById(Long id);

}

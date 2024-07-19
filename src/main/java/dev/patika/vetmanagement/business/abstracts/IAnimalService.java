package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;

public interface IAnimalService {
    Animal save(Animal animal);
    Page<Animal> cursor (int page, int pageSize);
    Animal get(long id);
    boolean delete(int id);

}

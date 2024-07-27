package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);
    Page<Vaccine> cursor (int page, int pageSize);
    Vaccine update(Vaccine vaccine);
    Vaccine get(long id);
    boolean delete(int id);
    List<Vaccine> findByAnimalId(int id);
    boolean existsById(Long vaccineId);

}

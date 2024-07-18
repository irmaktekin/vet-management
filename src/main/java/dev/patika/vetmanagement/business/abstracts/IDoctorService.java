package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;

public interface IDoctorService {
    Doctor save(Doctor category);
    Page<Doctor> cursor (int page, int pageSize);
    Doctor get(long id);
    boolean delete(int id);



}

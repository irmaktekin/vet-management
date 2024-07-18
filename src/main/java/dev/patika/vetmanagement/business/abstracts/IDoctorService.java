package dev.patika.vetmanagament.business.abstracts;

import dev.patika.vetmanagament.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface IDoctorService {
    Doctor save(Doctor category);
    Page<Doctor> cursor (int page, int pageSize);
    Doctor get(long id);
    boolean delete(int id);



}

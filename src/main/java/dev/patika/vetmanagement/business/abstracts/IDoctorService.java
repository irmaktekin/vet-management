package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    Page<Doctor> cursor (int page, int pageSize);
    Doctor update(Doctor doctor, List<Long> availableDateIds);
    Doctor get(long id);
    boolean delete(int id);
    Doctor getDoctorByName(String name);
    boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDate);



}

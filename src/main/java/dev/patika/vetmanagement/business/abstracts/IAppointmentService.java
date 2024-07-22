package dev.patika.vetmanagement.business.abstracts;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Appointment update(Appointment appointment);
    Page<Appointment> cursor (int page, int pageSize);
    Appointment get(long id);
    boolean delete(int id);
    List<Appointment> findAppointmentsByDoctorAndDateRange(String doctorName, LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> findAppointmentsByAnimalAndDateRange(String animalName, LocalDateTime startDate, LocalDateTime endDate);

}


package dev.patika.vetmanagement.business.abstracts;
import dev.patika.vetmanagement.entities.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Appointment update(Appointment appointment);
    Page<Appointment> cursor (int page, int pageSize);
    Appointment get(long id);
    boolean delete(int id);
    List<Appointment> findAppointmentsByDoctorAndDateRange(String doctorName, LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> findAppointmentsByAnimalAndDateRange(String animalName, LocalDateTime startDate, LocalDateTime endDate);
    void deleteByDoctorId(int doctorId);
    List<Appointment> findAppointmentsByAnimalIdAndDateRange(Long animalId, LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
}


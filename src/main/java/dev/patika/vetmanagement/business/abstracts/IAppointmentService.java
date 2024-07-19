package dev.patika.vetmanagement.business.abstracts;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.AvailableDate;
import org.springframework.data.domain.Page;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Page<Appointment> cursor (int page, int pageSize);
    Appointment get(long id);
    boolean delete(int id);
}
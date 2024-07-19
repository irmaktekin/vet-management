package dev.patika.vetmanagement.dao;
import dev.patika.vetmanagement.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppointmentRepo extends JpaRepository<Appointment,Integer> {
}

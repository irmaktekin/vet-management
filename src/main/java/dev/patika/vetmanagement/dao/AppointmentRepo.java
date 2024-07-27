package dev.patika.vetmanagement.dao;
import dev.patika.vetmanagement.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {
    @Query("SELECT a FROM Appointment a WHERE a.doctor.name = :doctorName AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByDoctorNameAndAppointmentDateBetween(
            @Param("doctorName") String doctorName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    @Query("SELECT a FROM Appointment a JOIN a.customer c JOIN c.animals an WHERE an.name = :animalName AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByAnimalNameAppointmentDateBetween(
            @Param("animalName") String animalName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    Optional<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);
    void deleteByDoctorId(int doctorId);

}

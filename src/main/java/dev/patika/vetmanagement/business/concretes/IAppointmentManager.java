package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAppointmentService;
import dev.patika.vetmanagement.core.exception.AppointmentConflictException;
import dev.patika.vetmanagement.core.utilities.AppointmentTimeValidator;
import dev.patika.vetmanagement.dao.AppointmentRepo;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IAppointmentManager implements IAppointmentService {

    private final AppointmentRepo appointmentRepo;

    public IAppointmentManager(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (!AppointmentTimeValidator.isHourAligned(appointment.getAppointmentDate())) {
            throw new IllegalArgumentException("Appointment time must be at the start of the hour.");
        }

        // Check if an appointment exists for the given doctor at the given time
        Optional<Appointment> existingAppointment = appointmentRepo
                .findByDoctorIdAndAppointmentDate(appointment.getDoctor().getId(), appointment.getAppointmentDate());

        if (existingAppointment.isPresent()) {
            throw new AppointmentConflictException("There is already an appointment at this time.");
        }

        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) {
        this.get(appointment.getId());
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    @Override
    public Appointment get(long id) {
        return this.appointmentRepo.findById((int)id).orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public List<Appointment> findAppointmentsByDoctorAndDateRange(String doctor, LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepo.findByDoctorNameAndAppointmentDateBetween(doctor, startDate, endDate);
    }

    @Override
    public List<Appointment> findAppointmentsByAnimalAndDateRange(String animalName, LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepo.findByAnimalNameAppointmentDateBetween(animalName, startDate, endDate);
    }


}

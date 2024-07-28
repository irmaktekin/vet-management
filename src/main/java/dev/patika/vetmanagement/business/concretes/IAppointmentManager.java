package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAppointmentService;
import dev.patika.vetmanagement.business.abstracts.IDoctorService;
import dev.patika.vetmanagement.core.exception.AppointmentConflictException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.core.utilities.AppointmentTimeValidator;
import dev.patika.vetmanagement.dao.AppointmentRepo;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import jakarta.transaction.Transactional;
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
    private IDoctorService doctorService;


    public IAppointmentManager(AppointmentRepo appointmentRepo, IDoctorService doctorService) {
        this.appointmentRepo = appointmentRepo;
        this.doctorService = doctorService;
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (!AppointmentTimeValidator.isHourAligned(appointment.getAppointmentDate())) {
            throw new NotFoundException("Appointment time must be at the start of the hour.");
        }

        // Check if an appointment exists for the given doctor at the given time
        Optional<Appointment> existingAppointment = appointmentRepo
                .findByDoctorIdAndAppointmentDate(appointment.getDoctor().getId(), appointment.getAppointmentDate());

        if (existingAppointment.isPresent()) {
            throw new NotFoundException("There is already an appointment at this time.");
        }

        boolean isAvailable = doctorService.isDoctorAvailable(appointment.getDoctor().getId(), appointment.getAppointmentDate());
        if (!isAvailable) {
            throw new NotFoundException("The doctor is not available at this time.");
        }

        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) {
        Appointment existAppointment = appointmentRepo.findById(Math.toIntExact(appointment.getId()))
                .orElseThrow(() -> new NotFoundException("Appointment with ID " + appointment.getId() + " does not exist"));

        this.get(appointment.getId());
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Appointment> appointmentPage = this.appointmentRepo.findAll(pageable);
        if (appointmentPage.isEmpty()) {
            throw new NotFoundException("No appointments found");
        }
        return this.appointmentRepo.findAll(pageable);
    }

    @Override
    public Appointment get(long id) {
        return this.appointmentRepo.findById((int)id).orElseThrow();
    }

    @Override
    public boolean delete(int id) {
            Appointment appointment = appointmentRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("No data found with ID: " + id));

            // Delete the customer
            appointmentRepo.delete(appointment);
            return true;

    }

    @Override
    public List<Appointment> findAppointmentsByDoctorAndDateRange(String doctor, LocalDateTime startDate, LocalDateTime endDate) {
        List<Appointment> appointments = appointmentRepo.findByDoctorNameAndAppointmentDateBetween(doctor, startDate, endDate);
        if (appointments.isEmpty()) {
            throw new NotFoundException("No appointments found for doctor " + doctor + " between " + startDate + " and " + endDate);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAppointmentsByAnimalAndDateRange(String animalName, LocalDateTime startDate, LocalDateTime endDate) {
        List<Appointment> appointments = appointmentRepo.findByAnimalNameAppointmentDateBetween(animalName, startDate, endDate);
        if (appointments.isEmpty()) {
            throw new NotFoundException("No appointments found for doctor " + animalName + " between " + startDate + " and " + endDate);
        }
        return appointments;
    }
    @Override
    @Transactional
    public void deleteByDoctorId(int doctorId) {
        appointmentRepo.deleteByDoctorId(doctorId);
    }

}

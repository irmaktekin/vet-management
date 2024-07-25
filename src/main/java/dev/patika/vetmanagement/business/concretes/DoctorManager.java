package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IDoctorService;
import dev.patika.vetmanagement.core.exception.AvailableDateNotFoundException;
import dev.patika.vetmanagement.dao.AppointmentRepo;
import dev.patika.vetmanagement.dao.AvailableDateRepo;
import dev.patika.vetmanagement.dao.DoctorRepo;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DoctorManager implements IDoctorService {
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    private final AvailableDateRepo availableDateRepo;

    public DoctorManager(DoctorRepo doctorRepo, AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo) {
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor);
    }

    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.doctorRepo.findAll(pageable);    }

    @Override
    public Doctor update(Doctor doctor, List<Long> availableDateIds) {
        List<AvailableDate> availableDates = availableDateIds.stream()
                .map(dateId -> availableDateRepo.findById(Math.toIntExact(dateId))
                        .orElseThrow(() -> new AvailableDateNotFoundException("Available date with ID " + dateId + " does not exist")))
                .collect(Collectors.toList());

        // Set the fetched available dates to the doctor
        doctor.setAvailableDates(availableDates);
        this.get(doctor.getId());
        return this.doctorRepo.save(doctor);    }

    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById((int)id).orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }

    @Override
    public Doctor getDoctorByName(String name) {
        return doctorRepo.findByName(name).orElse(null);
    }
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDate) {
        Optional<Appointment> existingAppointment = appointmentRepo.findByDoctorIdAndAppointmentDate(doctorId, appointmentDate);
        return !existingAppointment.isPresent();
    }
}

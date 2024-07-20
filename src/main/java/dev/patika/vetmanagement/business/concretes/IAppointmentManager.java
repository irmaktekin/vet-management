package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAppointmentService;
import dev.patika.vetmanagement.dao.AppointmentRepo;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IAppointmentManager implements IAppointmentService {

    private final AppointmentRepo appointmentRepo;

    public IAppointmentManager(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public Appointment save(Appointment appointment) {
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
}

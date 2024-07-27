package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.IAvailableDateService;
import dev.patika.vetmanagement.core.exception.AvailableDateNotFoundException;
import dev.patika.vetmanagement.core.exception.CustomUniqueConstraintViolationException;
import dev.patika.vetmanagement.dao.AvailableDateRepo;
import dev.patika.vetmanagement.dao.DoctorRepo;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final AvailableDateRepo availableDateRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo) {
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        try{
            return this.availableDateRepo.save(availableDate);
        }
        catch (DataIntegrityViolationException e){
            throw new CustomUniqueConstraintViolationException("The date " + availableDate.getAvailableDate() + " already exists.");
        }
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        this.get(availableDate.getId());
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(long id) {
        return availableDateRepo.findById((int) id)
                .orElseThrow(() -> new AvailableDateNotFoundException("Available date with ID " + id + " does not exist"));

    }

    @Override
    public boolean delete(int id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }
}

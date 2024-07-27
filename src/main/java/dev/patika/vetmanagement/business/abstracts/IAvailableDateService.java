package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import org.springframework.data.domain.Page;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);
    Page<AvailableDate> cursor (int page, int pageSize);
    AvailableDate update(AvailableDate availableDate);
    AvailableDate get(long id);
    boolean delete(int id);

}

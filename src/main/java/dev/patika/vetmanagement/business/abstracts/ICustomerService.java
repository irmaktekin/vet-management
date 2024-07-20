package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import org.springframework.data.domain.Page;

public interface ICustomerService {
    Customer save(Customer customer);
    Page<Customer> cursor (int page, int pageSize);
    Customer get(long id);
    boolean delete(int id);
}

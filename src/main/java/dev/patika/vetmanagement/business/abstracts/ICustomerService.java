package dev.patika.vetmanagement.business.abstracts;

import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer update(Customer customer);
    Page<Customer> cursor (int page, int pageSize);
    Customer get(long id);
    boolean delete(int id);
    List<Customer> findByName(String name);

}

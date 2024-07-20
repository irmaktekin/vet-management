package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.dao.CustomerRepo;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;

    public ICustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.customerRepo.findAll(pageable);
    }

    @Override
    public Customer get(long id) {
        return this.customerRepo.findById((int)id).orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

    @Override
    public List<Customer> findByName(String name) {
        return this.customerRepo.findByName(name);
    }
}

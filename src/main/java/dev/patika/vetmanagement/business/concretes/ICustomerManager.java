package dev.patika.vetmanagement.business.concretes;

import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.core.exception.DuplicateRecordException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.dao.CustomerRepo;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Customer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return customerRepo.save(customer);
        } catch (DataIntegrityViolationException e) {
            String rootCauseMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : "";
            if (rootCauseMessage.contains("customer_mail_key")) {
                throw new DuplicateRecordException("email", rootCauseMessage);
            }
            if(rootCauseMessage.contains("customer_phone_key")){
                throw new DuplicateRecordException("phone", rootCauseMessage);
            }
            throw e; // rethrow if it's a different DataIntegrityViolationException
        }
    }

    @Override
    public Customer update(Customer customer) {
        Customer existingCustomer = customerRepo.findById(Math.toIntExact(customer.getId()))
                .orElseThrow(() -> new NotFoundException("Customer with ID " + customer.getId() + " does not exist"));

        this.get(customer.getId());
        return this.customerRepo.save(customer);
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Customer> customerPage = this.customerRepo.findAll(pageable);
        if (customerPage.isEmpty()) {
            throw new NotFoundException("No customers found");
        }
        return customerPage;
    }

    @Override
    public Customer get(long id) {
        return customerRepo.findById((int) id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));


    }

    @Override
    public boolean delete(int id) {
 try{
            Customer customer = customerRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("No data found with ID: " + id));

            // Delete the customer
            customerRepo.delete(customer);
        }
        catch (Exception e){
            throw new NotFoundException("No data found with ID: "+id);
        }

        return true;
    }

    @Override
    public List<Customer> findByName(String name) {
        List<Customer> customers = this.customerRepo.findByName(name);

        if (customers.isEmpty()) {
            throw new NotFoundException("No customers found with the name: " + name);
        }

        return customers;    }
}

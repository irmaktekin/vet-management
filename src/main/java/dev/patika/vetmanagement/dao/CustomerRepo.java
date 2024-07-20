package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    List<Customer> findByName(String name);

}

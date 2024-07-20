package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Integer> {
    List<Animal> findByName(String name);

    @Query("SELECT a FROM Animal a JOIN a.customer c WHERE c.name = :customerName")
    List<Animal> findByCustomerName(String customerName);

}

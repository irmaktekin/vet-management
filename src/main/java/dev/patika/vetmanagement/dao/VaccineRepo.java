package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Integer> {
    @Query("SELECT v FROM Vaccine v JOIN v.animals a WHERE a.id = :animalId")
    List<Vaccine> findByAnimalId(@Param("animalId")int animalId);
    @Query("SELECT v FROM Vaccine v WHERE v.id = :id")
    Optional<Vaccine> findVaccinesByCodeAndName(@Param("id") Long id);
}

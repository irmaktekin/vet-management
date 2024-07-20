package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Integer> {
    @Query("SELECT v FROM Vaccine v JOIN v.animals a WHERE a.id = :animalId")
    List<Vaccine> findByAnimalId(@Param("animalId")int animalId);

}

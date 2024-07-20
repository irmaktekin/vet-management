package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Integer> {

}

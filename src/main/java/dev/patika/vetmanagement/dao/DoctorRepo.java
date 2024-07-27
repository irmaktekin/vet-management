package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
    Optional<Doctor> findByName(String name);

}
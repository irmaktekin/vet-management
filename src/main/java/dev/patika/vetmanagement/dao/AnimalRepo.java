package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Integer> {
}

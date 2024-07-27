package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Integer> {
    boolean existsById(Long id);
}

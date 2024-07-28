package dev.patika.vetmanagement.dao;

import dev.patika.vetmanagement.entities.AnimalVaccine;
import dev.patika.vetmanagement.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalVaccineRepository extends JpaRepository<AnimalVaccine,Long> {
    //@Query("SELECT av FROM AnimalVaccine av WHERE av.animal.id = :animalId AND av.vaccine.code = :code AND av.vaccine.name = :name AND av.vaccine.protectionFinishDate > CURRENT_DATE")
    @Query("SELECT av FROM AnimalVaccine av " +
            "JOIN av.vaccine v " +
            "WHERE av.animal.id = :animalId " +
            "AND v.code = :vaccineCode " +
            "AND v.name = :vaccineName " +
            "AND v.protectionFinishDate > CURRENT_DATE")
    Optional<AnimalVaccine> findActiveAnimalVaccinesByVaccineId(@Param("animalId") Long animalId,
                                                                @Param("vaccineCode") String vaccineCode,
                                                                @Param("vaccineName") String vaccineName);

    @Query("SELECT av FROM AnimalVaccine av JOIN av.vaccine v WHERE v.protectionFinishDate BETWEEN :startDate AND :endDate")
    Page<AnimalVaccine> findAnimalVaccinesByProtectionFinishDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}

package dev.patika.vetmanagament.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="available_date")
@Data
public class AvailableDate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="date_id")
    private Long id;

    private LocalDate availableDate;

    // Many-to-Many relationship with Doctor
    @ManyToMany(mappedBy = "availableDates")
    private Set<Doctor> doctors = new HashSet<>();
}

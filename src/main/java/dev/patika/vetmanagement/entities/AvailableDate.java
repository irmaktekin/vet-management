package dev.patika.vetmanagement.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="available_date")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="date_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private LocalDate availableDate;

    @JsonIgnore
    // Many-to-Many relationship with Doctor
    @ManyToMany(mappedBy = "availableDates",cascade = CascadeType.ALL)
    private Set<Doctor> doctors = new HashSet<>();
}

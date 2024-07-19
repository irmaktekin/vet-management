package dev.patika.vetmanagement.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="animal")
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="animal_id")
    private Long id;

    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToMany
    @JoinTable(
            name = "animal_vaccine",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "vaccine_id")
    )
    private Set<Vaccine> vaccines = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


}

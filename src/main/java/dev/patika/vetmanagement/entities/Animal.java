package dev.patika.vetmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="animal")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonIgnore
    @ManyToMany(cascade =CascadeType.ALL)
    @JoinTable(
            name = "animal_vaccine",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "vaccine_id")
    )
    private Set<Vaccine> vaccines = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;





}

package dev.patika.vetmanagament.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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


}

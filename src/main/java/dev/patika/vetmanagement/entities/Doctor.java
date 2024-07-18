package dev.patika.vetmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="doctor")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="doctor_id")
    private Long id;

    @NotNull
    private String name;
    private String phone;

    @Email
    private String mail;
    private String address;
    private String city;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "doctor_available_dates",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "date_id") }
    )
    private Set<AvailableDate> availableDates = new HashSet<>();
}

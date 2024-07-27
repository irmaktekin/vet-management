package dev.patika.vetmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="doctor")
@Data
@ToString(exclude = "availableDates")

public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="doctor_id")
    private Long id;
    @NotNull
    private String name;
    @Email
    @Column(unique = true)
    private String mail;
    @Column(unique = true)
    private String phone;
    private String address;
    private String city;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctor_available_dates",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "date_id") }
    )
    private List<AvailableDate> availableDates = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Animal> animals = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();
}

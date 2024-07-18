package dev.patika.vetmanagament.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="appointmentdate")

@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="appointment_date")

    private LocalDate appointmentDate;
}

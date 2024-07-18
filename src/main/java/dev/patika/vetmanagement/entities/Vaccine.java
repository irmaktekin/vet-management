package dev.patika.vetmanagement.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="vaccine")
@Data
public class Vaccine {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="vaccine_id")
    private Long id;

    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
}

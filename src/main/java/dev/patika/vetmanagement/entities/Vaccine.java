package dev.patika.vetmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="vaccine")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccine {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="vaccine_id")
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;

    @JsonIgnore // Ignore animals during serialization

    @ManyToMany(mappedBy = "vaccines")
    private Set<Animal> animals = new HashSet<>();
}

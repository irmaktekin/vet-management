package dev.patika.vetmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String name;
    @NotNull
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;

    @JsonIgnore // Ignore animals during serialization

    @ManyToMany(mappedBy = "vaccines",cascade = CascadeType.ALL)
    private Set<Animal> animals = new HashSet<>();
}

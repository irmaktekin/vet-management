package dev.patika.vetmanagement.entities;

import dev.patika.vetmanagement.core.utilities.AnimalVaccineId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animal_vaccine")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalVaccine {
    @EmbeddedId
    private AnimalVaccineId id;

    @ManyToOne
    @MapsId("animalId")
    @JoinColumn(name = "animal_id", nullable = false,insertable = false, updatable = false)
    private Animal animal;

    @ManyToOne
    @MapsId("vaccineId")
    @JoinColumn(name = "vaccine_id", nullable = false,insertable = false, updatable = false)
    private Vaccine vaccine;

    public AnimalVaccine(Animal animal,Vaccine vaccine){
        this.animal=animal;
        this.vaccine = vaccine;
        this.id = new AnimalVaccineId(animal.getId(),vaccine.getId());
    }
}

package dev.patika.vetmanagement.dto.request.vaccine;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {
    private long id;
    private String name;

    @NotNull
    private String code;
    @NotNull
    private LocalDate protectionStartDate;
    @NotNull
    private LocalDate protectionFinishDate;

}

package dev.patika.vetmanagement.dto.request.vaccineAnimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacinneAnimalDateRangeRequest {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

}

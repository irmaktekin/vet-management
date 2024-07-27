package dev.patika.vetmanagement.dto.request.vaccineAnimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacinneAnimalDateRangeRequest {
    private LocalDate startDate;
    private LocalDate endDate;

}

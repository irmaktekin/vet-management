package dev.patika.vetmanagement.dto.request.availabledate;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDateSaveRequest {
    @NotNull
    private LocalDate availableDate;

}

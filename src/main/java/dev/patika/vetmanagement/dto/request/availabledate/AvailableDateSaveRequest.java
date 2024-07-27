package dev.patika.vetmanagement.dto.request.availabledate;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDateSaveRequest {
    private LocalDate availableDate;

}

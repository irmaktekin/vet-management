package dev.patika.vetmanagement.dto.response.availabledate;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AvailableDateResponse {
    private Long id;
    private LocalDate availableDate;

}

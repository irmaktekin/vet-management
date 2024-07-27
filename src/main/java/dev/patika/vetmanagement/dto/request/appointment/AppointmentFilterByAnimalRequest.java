package dev.patika.vetmanagement.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFilterByAnimalRequest {
    private String animalName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

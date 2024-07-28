package dev.patika.vetmanagement.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @NotNull(message = "ID must not be null")
    private Long id;
    @NotNull(message = "Date must not be null")
    private LocalDateTime appointmentDate;
    @NotNull(message = "Animal ID must not be null")
    private Long animalId;
    @NotNull(message = "Doctor ID must not be null")
    private Long doctorId;
}

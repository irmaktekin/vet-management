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
    @NotNull
    private Long id;
    @NotNull
    private LocalDateTime appointmentDate;
    @NotNull
    private Long customerId;
    @NotNull
    private Long doctorId;
}

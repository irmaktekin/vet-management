package dev.patika.vetmanagement.dto.response.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private LocalDate appointmentDate;
    private Long customerId;
    private Long doctorId;
}

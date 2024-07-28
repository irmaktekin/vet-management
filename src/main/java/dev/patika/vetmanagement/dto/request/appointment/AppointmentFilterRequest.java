package dev.patika.vetmanagement.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFilterRequest {
   @NotNull
   private String doctorName;
   @NotNull
   private LocalDateTime startDate;
   @NotNull
   private LocalDateTime endDate;
}

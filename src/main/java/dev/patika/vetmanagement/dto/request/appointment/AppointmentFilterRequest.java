package dev.patika.vetmanagement.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFilterRequest {
   private String doctorName;
   private LocalDateTime startDate;
   private LocalDateTime endDate;
}

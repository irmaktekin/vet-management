package dev.patika.vetmanagement.dto.request.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorSaveRequest {
    @NotNull
    private String name;

    @NotNull(message = "Phone must have a value.")
    private String phone;
    @NotNull(message = "Email must have a value")
    @Email
    private String mail;
    @NotNull(message = "Address must have a value.")
    private String address;
    @NotNull(message = "City must have a value.")
    private String city;

    @NotNull(message = "Doctor must have at least 1 available date.")
    private List<Long> availableDateIds;
}

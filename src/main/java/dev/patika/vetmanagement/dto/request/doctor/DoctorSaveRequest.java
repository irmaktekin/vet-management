package dev.patika.vetmanagement.dto.request.doctor;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorSaveRequest {
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
    @NotNull
    private List<Long> availableDateIds;
}

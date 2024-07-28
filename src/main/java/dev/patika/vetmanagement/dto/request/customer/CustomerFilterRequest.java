package dev.patika.vetmanagement.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilterRequest {
    @NotNull
    private String name;
}

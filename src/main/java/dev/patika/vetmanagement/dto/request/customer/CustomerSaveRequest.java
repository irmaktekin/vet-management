package dev.patika.vetmanagement.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {
    @NotNull(message = "Category name must have a value.")
    private String name;

    @NotNull(message = "Phone must have a value.")
    private String phone;

    @NotNull(message = "Mail must have a value.")
    private String mail;

    @NotNull(message = "Address must have a value.")
    private String  address;

    @NotNull(message = "City must have a value.")
    private String city;

    private List<Long> animalIds;

}

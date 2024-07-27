package dev.patika.vetmanagement.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
    private Long id;
    private String name;

    private String phone;

    private String mail;
    private String  address;
    private String city;

    private List<Long> animalIds;
}

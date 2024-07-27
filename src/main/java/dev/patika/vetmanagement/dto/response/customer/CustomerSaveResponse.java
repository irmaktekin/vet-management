package dev.patika.vetmanagement.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveResponse {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String city;
    private String  mail;
}

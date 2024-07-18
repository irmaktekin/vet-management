package dev.patika.vetmanagement.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long id;

    private String name;

    private String phone;

    private String mail;

    private String  address;

    private String city;


}

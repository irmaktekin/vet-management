package dev.patika.vetmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Animal> animals = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();

}

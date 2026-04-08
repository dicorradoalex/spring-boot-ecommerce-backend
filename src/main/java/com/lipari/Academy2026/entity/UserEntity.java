package com.lipari.Academy2026.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Equals e HashCode su campi indicati
@NoArgsConstructor // Costruttore senza parametri per JPA quando instanzia l'entità per recuperare i dati dal db
@AllArgsConstructor // Costruttore canonico
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    // "nullable = false" non accetta valori nulli
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    // "unique = true" garantisce l'assenza di duplicati
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(length = 200)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 50)
    private String country;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;


}

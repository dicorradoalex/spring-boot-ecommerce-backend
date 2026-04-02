package com.lipari.Academy2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
}

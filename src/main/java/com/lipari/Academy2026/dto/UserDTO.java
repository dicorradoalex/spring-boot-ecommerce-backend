package com.lipari.Academy2026.dto;

import com.lipari.Academy2026.entity.OrderEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;
import java.util.List;

public record UserDTO(
        UUID id,

        @NotBlank
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "Il nome è obbligatorio")
        String name,

        @NotBlank(message = "Il cognome è obbligatorio")
        String surname,

        String address,
        String city,
        String country,
        List<OrderDTO> orders) {
}

package com.lipari.Academy2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Record per il trasferimento dei dati del Prodotto.
 */
public record ProductDTO(
        UUID id,

        @NotBlank(message = "Il nome è obbligatorio")
        String name,

        @NotNull(message = "Il prezzo è obbligatorio")
        @Positive(message = "Il prezzo deve essere maggiore di zero")
        BigDecimal price,

        String description,

        @NotNull(message = "La quantità disponibile è obbligatoria")
        @PositiveOrZero(message = "La quantità non può essere negativa")
        Integer stock,

        @NotNull(message = "La categoria è obbligatoria")
        CategoryDTO category) {
}

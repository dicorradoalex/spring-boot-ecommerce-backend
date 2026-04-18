package com.lipari.Academy2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO utilizzato per le richieste di creazione e aggiornamento prodotti.
 * NOTA: Non contiene l'ID (generato dal server) e usa categoryId per semplicità.
 */
public record ProductRequestDTO(

        @NotBlank(message = "Il nome del prodotto è obbligatorio")
        String name,

        @NotNull(message = "Il prezzo è obbligatorio")
        @Positive(message = "Il prezzo deve essere maggiore di zero")
        BigDecimal price,

        String description,

        @NotNull(message = "La quantità disponibile è obbligatoria")
        @PositiveOrZero(message = "La quantità non può essere negativa")
        Integer stock,

        @NotNull(message = "L'ID della categoria è obbligatorio")
        UUID categoryId
) {}

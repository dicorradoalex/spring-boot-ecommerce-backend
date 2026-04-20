package com.lipari.Academy2026.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/**
 * Rappresenta la richiesta di aggiunta di un prodotto al carrello.
 * Utilizzato dal controller per ricevere ID prodotto e quantità.
 */

public record CartItemRequestDTO(
        @NotNull(message = "L'ID del prodotto è obbligatorio")
        UUID productId,

        @NotNull(message = "La quantità è obbligatoria")
        @Positive(message = "La quantità deve essere maggiore di zero")
        Integer quantity
) {}

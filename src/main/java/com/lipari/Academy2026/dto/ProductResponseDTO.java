package com.lipari.Academy2026.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Record per l'invio dei dati del Prodotto verso il client.
 * NOTA: Essendo un DTO di sola risposta, non necessita di annotazioni di validazione.
 */
public record ProductResponseDTO(
        UUID id,
        String name,
        BigDecimal price,
        String description,
        Integer stock,
        CategoryResponseDTO category
) {}

package com.lipari.Academy2026.dto;

import java.util.UUID;

/**
 * DTO per l'invio dei dati della categoria al client.
 */
public record CategoryResponseDTO(
        UUID id,
        String name
) {}
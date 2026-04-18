package com.lipari.Academy2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizzato per le richieste di creazione e aggiornamento categorie.
 */
public record CategoryRequestDTO(
        
        @NotBlank(message = "Il nome della categoria è obbligatorio")
        @Size(max = 100, message = "Il nome della categoria non può superare i 100 caratteri")
        String name
) {}


package com.lipari.Academy2026.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Rappresenta i dati inviati dal client per effettuare l'autenticazione.
 *
 * @param email L'indirizzo email dell'utente. Deve essere un'email valida.
 * @param password La password dell'utente. Campo obbligatorio per il login.
 */

public record LoginRequestDTO(

        @NotBlank(message = "Le credenziali sono obbligatorie")
        @Email(message = "Inserire un'email valida")
        String email,

        @NotBlank(message = "Le credenziali sono obbligatorie")
        String password
) {
}

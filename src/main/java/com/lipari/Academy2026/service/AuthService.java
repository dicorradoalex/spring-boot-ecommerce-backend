package com.lipari.Academy2026.service;

import com.lipari.Academy2026.dto.AuthResponseDTO;
import com.lipari.Academy2026.dto.LoginRequestDTO;

/**
 * Interfaccia per i servizi di autenticazione.
 */
public interface AuthService {

    /**
     * Autentica un utente tramite email e password e restituisce un JWT.
     *
     * @param request DTO contenente email e password
     * @return DTO contenente l'access token generato
     */
    AuthResponseDTO login(LoginRequestDTO request);
}
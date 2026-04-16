package com.lipari.Academy2026.service.impl;

import com.lipari.Academy2026.config.JwtService;
import com.lipari.Academy2026.dto.AuthResponseDTO;
import com.lipari.Academy2026.dto.LoginRequestDTO;
import com.lipari.Academy2026.repository.UserRepository;
import com.lipari.Academy2026.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        // Validazione credenziali
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // Recupero utente dal DB
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(); // già validato dall'autenticazione

        // Generazione JWT
        String jwtToken = jwtService.generateToken(user);

        // Risposta
        return new AuthResponseDTO(jwtToken, "Bearer");
    }
}


/*
    NOTE DIDATTICHE - [AuthServiceImpl]

    - AuthenticationManager:
      Valida automaticamente email e password usando
      UserDetailsService + PasswordEncoder.

    - UsernamePasswordAuthenticationToken:
      Contenitore standard di Spring per le credenziali.

    - Flusso:
      1. Autenticazione credenziali
      2. Recupero utente
      3. Generazione JWT
      4. Restituzione token al client

    - Bearer:
      Indica che il token deve essere usato nell'header:
      Authorization: Bearer <token>
*/
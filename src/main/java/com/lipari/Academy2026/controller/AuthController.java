package com.lipari.Academy2026.controller;

import com.lipari.Academy2026.dto.AuthResponseDTO;
import com.lipari.Academy2026.dto.LoginRequestDTO;
import com.lipari.Academy2026.dto.UserRegistrationRequestDTO;
import com.lipari.Academy2026.dto.UserResponseDTO;
import com.lipari.Academy2026.service.AuthService;
import com.lipari.Academy2026.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Endpoint pubblico (vedi SecurityConfig)
@RequiredArgsConstructor
public class AuthController {

    // Dipendenze
    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRegistrationRequestDTO registrationDTO) {

        // Registrazione utente
        UserResponseDTO response = this.userService.registerUser(registrationDTO);

        // Restituiamo 201 Created + utente (senza password)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginDTO) {

        // Autenticazione utente + generazione JWT
        AuthResponseDTO response = this.authService.login(loginDTO);

        // Risposta 200 OK con token
        return ResponseEntity.ok(response);
    }
}
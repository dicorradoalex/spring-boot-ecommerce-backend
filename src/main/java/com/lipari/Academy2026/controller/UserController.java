package com.lipari.Academy2026.controller;

import com.lipari.Academy2026.dto.UserDTO;
import com.lipari.Academy2026.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // Constructor Injection con Lombok
public class UserController {

    // Dipendenze
    private final UserService userService;

    // Metodi CRUD

    @GetMapping("/new")
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUser(UUID id) {

    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> updateUser(UUID id) {

    }
}

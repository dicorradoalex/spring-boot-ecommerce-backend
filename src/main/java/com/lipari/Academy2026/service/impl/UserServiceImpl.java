package com.lipari.Academy2026.service.impl;

import com.lipari.Academy2026.repository.UserRepository;
import com.lipari.Academy2026.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // Constructor Injection con Lombok
@Service
public class UserServiceImpl implements UserService {

    // Dipendenze
    private final UserRepository userRepository;
    private final UserController userController;
}

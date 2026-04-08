package com.lipari.Academy2026.service.impl;

import com.lipari.Academy2026.dto.UserDTO;
import com.lipari.Academy2026.entity.UserEntity;
import com.lipari.Academy2026.exception.AlreadyExistsException;
import com.lipari.Academy2026.exception.ResourceNotFoundException;
import com.lipari.Academy2026.mapper.UserMapper;
import com.lipari.Academy2026.repository.UserRepository;
import com.lipari.Academy2026.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Constructor Injection con Lombok
@Transactional(readOnly = true) // Imposta tutto il service come sola lettura di default
public class UserServiceImpl implements UserService {

    // Dipendenze -> final per Constructor Injection
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Costruttore "invisibile" Generato da Lombok -> Constructor Injection

    // Metodi CRUD: recupera Entity dal Repository e restituisci DTO al Controller
    // Metodi CRUD: ricevi DTO dal Controller e salva nel DB

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        // Controlla se l'email è già nel db
        if (userRepository.existsByEmail(userDTO.email()))
            throw new AlreadyExistsException("L'email " + userDTO.email() + " è già registrata.");
        // Mappa e salva
        UserEntity user = userMapper.toEntity(userDTO);
        UserEntity savedUser = userRepository.save(user);
        // Restituisci il DTO al Controller
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDTO getUser(UUID id) {
        Optional<UserEntity> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent())
            return this.userMapper.toDto(userOptional.get());
        else
            throw new ResourceNotFoundException("Utente con ID: " + id + " non trovato.");
    }

    public void deleteUser(UUID id) {
        // TODO

    }

    public UserDTO updateUser(UserDTO userDTO) {

    }

    public List<UserDTO> getAllUsers() {
        // TODO
    }



}

package com.lipari.Academy2026.service;

import com.lipari.Academy2026.dto.UserRegistrationRequestDTO;
import com.lipari.Academy2026.dto.UserResponseDTO;
import com.lipari.Academy2026.entity.UserEntity;
import org.mapstruct.MappingTarget;


import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO getUser(UUID id);

    void deleteUser(UUID id);

    UserResponseDTO updateUser(UserResponseDTO userResponseDTO);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO registerUser(UserRegistrationRequestDTO registrationDTO);

}

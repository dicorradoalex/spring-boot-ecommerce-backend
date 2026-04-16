package com.lipari.Academy2026.service;

import com.lipari.Academy2026.dto.UserResponseDTO;


import java.util.List;
import java.util.UUID;

public interface UserService {

    public UserResponseDTO createUser(UserResponseDTO userResponseDTO);

    public UserResponseDTO getUser(UUID id);

    public void deleteUser(UUID id);

    public UserResponseDTO updateUser(UserResponseDTO userResponseDTO);

    public List<UserResponseDTO> getAllUsers();
}

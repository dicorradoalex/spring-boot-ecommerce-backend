package com.lipari.Academy2026.service;

import com.lipari.Academy2026.dto.UserDTO;


import java.util.List;
import java.util.UUID;

public interface UserService {

    public UserDTO createUser(UserDTO userDTO);

    public UserDTO getUser(UUID id);

    public void deleteUser(UUID id);

    public UserDTO updateUser(UserDTO userDTO);

    public List<UserDTO> getAllUsers();
}

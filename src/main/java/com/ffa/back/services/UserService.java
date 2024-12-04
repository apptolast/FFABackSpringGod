package com.ffa.back.services;

import com.ffa.back.dto.UserDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    Optional<UserDTO> updateUser(Long id, UserUpdateRequestDTO userUpdateRequest);
    // Otros métodos según necesidad
}

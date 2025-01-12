package com.ffa.back.services;

import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserResponseDTO> getAllUsers();

    Optional<UserResponseDTO> getUserById(Long id);

    Optional<UserResponseDTO> getUser(String email);

    Optional<UserResponseDTO> updateUser(Long id, UserUpdateRequestDTO userUpdateRequest);
}

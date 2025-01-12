package com.ffa.back.services;

import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
import com.ffa.back.mapper.UserMapper;
import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.debug("Request to get all Users");
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Optional<UserResponseDTO> getUser(String email) {
        log.debug("Request to get User by email : {}", email);
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    public Optional<UserResponseDTO> updateUser(Long id, UserUpdateRequestDTO userUpdateRequest) {
        log.debug("Request to update User : {}", id);
        return userRepository.findById(id)
                .map(user -> updateUserFields(user, userUpdateRequest));
    }

    private UserResponseDTO updateUserFields(User user, UserUpdateRequestDTO updateRequest) {
        if (updateRequest.getLanguage() != null) {
            Language lang = languageRepository.findByLanguage(updateRequest.getLanguage())
                    .orElseGet(() -> languageRepository.save(new Language(updateRequest.getLanguage())));
            user.setLanguage(lang);
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isBlank()) {
            user.setEmail(updateRequest.getEmail());
        }

        return userMapper.toDto(userRepository.save(user));
    }


}
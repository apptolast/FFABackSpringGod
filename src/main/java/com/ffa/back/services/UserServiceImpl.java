package com.ffa.back.services;

import com.ffa.back.dto.UserDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
import com.ffa.back.mappers.UserMapper;
import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import com.ffa.back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           LanguageRepository languageRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO);
    }

    @Override
    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserUpdateRequestDTO userUpdateRequest) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Actualizar los campos permitidos
            if (userUpdateRequest.getLanguage() != null) {
                Optional<Language> languageOpt = languageRepository.findByLanguage(userUpdateRequest.getLanguage());
                if (languageOpt.isEmpty()) {
                    languageOpt = Optional.of(languageRepository.save(new Language(userUpdateRequest.getLanguage())));
                }
                user.setLanguage(languageOpt.get());
            }

            // Actualizar otros campos si es necesario (ejemplo: email)
            if (userUpdateRequest.getEmail() != null) {
                user.setEmail(userUpdateRequest.getEmail());
            }

            // Guardar cambios
            userRepository.save(user);

            // Mapear a UserDTO y retornar
            UserDTO userDTO = userMapper.toUserDTO(user);
            return Optional.of(userDTO);
        } else {
            return Optional.empty();
        }
    }

    // Otros métodos según necesidad
}
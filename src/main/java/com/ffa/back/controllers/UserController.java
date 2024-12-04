package com.ffa.back.controllers;

import com.ffa.back.dto.UserDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
import com.ffa.back.mappers.UserMapper;
import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("familyfilmapp/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserRepository userRepository,
                          LanguageRepository languageRepository,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/test/jenkins")
    public ResponseEntity<List<UserDTO>> getAllUsersTest() {
        return getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            UserDTO userDTO = userMapper.toUserDTO(userOpt.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO userUpdateRequest) {

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
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}



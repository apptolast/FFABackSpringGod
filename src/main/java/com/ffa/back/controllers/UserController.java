package com.ffa.back.controllers;

import com.ffa.back.dto.UserDTO;
import com.ffa.back.dto.UserResponseDTO;
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

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(userMapper::toUserDTO)
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @CrossOrigin
    @GetMapping("/test/jenkins")
    public ResponseEntity<List<UserResponseDTO>> getAllUsersTest() {
        return getAllUsers();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserResponseDTO userDTO = convertToUserResponseDTO(userMapper.toUserDTO(user));
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
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

            UserResponseDTO userDTO = convertToUserResponseDTO(userMapper.toUserDTO(user));
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Método auxiliar para convertir UserDTO a UserResponseDTO.
     * Esto es necesario si UserResponseDTO es diferente a UserDTO.
     */
    private UserResponseDTO convertToUserResponseDTO(UserDTO userDTO) {
        return new UserResponseDTO(
                userDTO.getId(),
                userDTO.getFirebaseUuid(),
                userDTO.getEmail(),
                userDTO.getProvider(),
                userDTO.getRole(),
                userDTO.getSub(),
                userDTO.getAuthTime(),
                userDTO.getIat(),
                userDTO.getExp(),
                userDTO.getEmailVerified(),
                userDTO.getSignInProvider(),
                userDTO.getLanguageId() != null ? getLanguageNameById(userDTO.getLanguageId()) : null
        );
    }


    /**
     * Método auxiliar para obtener el nombre del lenguaje por su ID.
     */
    private String getLanguageNameById(Long languageId) {
        Optional<Language> languageOpt = languageRepository.findById(languageId);
        return languageOpt.map(Language::getLanguage).orElse(null);
    }

}



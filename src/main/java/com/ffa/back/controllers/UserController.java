package com.ffa.back.controllers;

import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;


    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getProvider(),
                        user.getLanguage().getLanguage()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserResponseDTO userDTO = new UserResponseDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getProvider(),
                    user.getLanguage().getLanguage());
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
                Optional<Language> language = languageRepository.findByLanguage(userUpdateRequest.getLanguage()).blockOptional();
                if (language.isEmpty()) {
                    language = Optional.of(languageRepository.save(new Language(userUpdateRequest.getLanguage())));
                }
                user.setLanguage(language.get());
            }

            // Guardar cambios
            userRepository.save(user);

            UserResponseDTO userDTO = new UserResponseDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getProvider(),
                    user.getLanguage().getLanguage());

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}



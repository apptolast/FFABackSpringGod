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
        return getListResponseEntity();
    }

    private ResponseEntity<List<UserResponseDTO>> getListResponseEntity() {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getFirebaseUuid(),
                        user.getEmail(),
                        user.getProvider(),
                        user.getRole(),
                        user.getSub(),
                        user.getAuthTime(),
                        user.getIat(),
                        user.getExp(),
                        user.getEmailVerified(),
                        user.getSignInProvider(),
                        user.getLanguage() != null ? user.getLanguage().getLanguage() : null
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @CrossOrigin
    @GetMapping("/test/jenkins")
    public ResponseEntity<List<UserResponseDTO>> getAllUsersTest() {
        return getListResponseEntity();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return getUserResponseDTOResponseEntity(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<UserResponseDTO> getUserResponseDTOResponseEntity(User user) {
        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getFirebaseUuid(),
                user.getEmail(),
                user.getProvider(),
                user.getRole(),
                user.getSub(),
                user.getAuthTime(),
                user.getIat(),
                user.getExp(),
                user.getEmailVerified(),
                user.getSignInProvider(),
                user.getLanguage() != null ? user.getLanguage().getLanguage() : null);
        return ResponseEntity.ok(userDTO);
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
                Optional<Language> language = languageRepository.findByLanguage(userUpdateRequest.getLanguage());
                if (language.isEmpty()) {
                    language = Optional.of(languageRepository.save(new Language(userUpdateRequest.getLanguage())));
                }
                user.setLanguage(language.get());
            }

            // Guardar cambios
            userRepository.save(user);

            return getUserResponseDTOResponseEntity(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}



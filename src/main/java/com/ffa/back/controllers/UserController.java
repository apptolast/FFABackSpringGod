package com.ffa.back.controllers;

import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
import com.ffa.back.services.FirebaseAuthService;
import com.ffa.back.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("familyfilmapp/api/users")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {


    private final IUserService userService;
    private final FirebaseAuthService firebaseAuthService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @CrossOrigin
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(@RequestHeader("Authorization") String authHeader) {
        try {
            String email = firebaseAuthService.verifyToken(authHeader).getEmail();
            return userService.getUser(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error authenticating user", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO userUpdateRequest) {

        return userService.updateUser(id, userUpdateRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}



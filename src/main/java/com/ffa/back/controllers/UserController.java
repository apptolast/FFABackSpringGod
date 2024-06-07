package com.ffa.back.controllers;

import com.ffa.back.models.User;
import com.ffa.back.models.UserTokenReponse;
import com.ffa.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("familyfilmapp/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/backend/login")
    public ResponseEntity<UserTokenReponse> login(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        return null;
    }

}

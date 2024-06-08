package com.ffa.back.controllers;

import com.ffa.back.models.User;
import com.ffa.back.models.UserTokenReponse;
import com.ffa.back.repositories.UserRepository;
import com.ffa.back.services.FirebaseAuthService;
import com.ffa.back.services.GenericService;
import com.ffa.back.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("familyfilmapp/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private GenericService<User, Long> genericService;

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
        String uid = firebaseAuthService.getUidUser(user.getEmail());
        String customToken = firebaseAuthService.generateCustomToken(uid);
        Map tokens = firebaseAuthService.idTokenForLogin(customToken);
        return ResponseEntity.ok(new UserTokenReponse((String) tokens.get("idToken"), (String) tokens.get("refreshToken"), (String) tokens.get("expiresIn")));
    }

    @CrossOrigin
    @PostMapping("/backend/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> userOptional = genericService.findByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            User savedUser = (User) genericService.save(user);
            Map<String, String> response = new HashMap<>();
            String uid = firebaseAuthService.getUidUser(savedUser.getEmail());
            String customToken = firebaseAuthService.generateCustomToken(uid);
            Map tokens = firebaseAuthService.idTokenForLogin(customToken);
            UserTokenReponse userTokenReponse = new UserTokenReponse((String) tokens.get("idToken"), (String) tokens.get("refreshToken"), (String) tokens.get("expiresIn"));
            response.put("message", "User registered successfully");
            response.put("idToken", userTokenReponse.getIdToken());
            response.put("refreshToken", userTokenReponse.getRefreshToken());
            response.put("expiresIn", userTokenReponse.getExpiresIn());
            return ResponseEntity.ok().body(response);
        }  else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            return ResponseEntity.badRequest().body(response);
        }
    }

}

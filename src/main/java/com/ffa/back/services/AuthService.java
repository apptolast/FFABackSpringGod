package com.ffa.back.services;


import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.models.UserTokenReponse;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public ResponseEntity<UserTokenReponse> login(User user) {
        String uid = firebaseAuthService.getUidUser(user.getEmail());
        String customToken = firebaseAuthService.generateCustomToken(uid);
        Map tokens = firebaseAuthService.idTokenForLogin(customToken);
        return ResponseEntity.ok(new UserTokenReponse((String) tokens.get("idToken"), (String) tokens.get("refreshToken"), (String) tokens.get("expiresIn")));
    }

    public ResponseEntity<?> register(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            Language language = languageRepository.findByLanguage(user.getLanguage().getLanguage());
            if (language == null) {
                language = languageRepository.save(user.getLanguage());
            }
            user.setLanguage(language);
            User savedUser = userRepository.save(user);
            Map<String, String> response = new HashMap<>();
            firebaseAuthService.createFirebaseUser(user.getEmail());
            String uid = firebaseAuthService.getUidUser(savedUser.getEmail());
            String customToken = firebaseAuthService.generateCustomToken(uid);
            Map tokens = firebaseAuthService.idTokenForLogin(customToken);
            UserTokenReponse userTokenReponse = new UserTokenReponse((String) tokens.get("idToken"), (String) tokens.get("refreshToken"), (String) tokens.get("expiresIn"));
            response.put("message", "User registered successfully");
            response.put("idToken", userTokenReponse.getIdToken());
            response.put("refreshToken", userTokenReponse.getRefreshToken());
            response.put("expiresIn", userTokenReponse.getExpiresIn());
            return ResponseEntity.ok().body(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User already exists, please login.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
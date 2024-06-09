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
            User savedUser = saveNewUser(user);
            Map<String, String> response = generateResponse(savedUser);
            return ResponseEntity.ok().body(response);
        } else {
            return buildUserExistsResponse();
        }
    }


    private User saveNewUser(User user) {
        Language language = findOrCreateLanguage(user.getLanguage().getLanguage());
        user.setLanguage(language);
        User savedUser = userRepository.save(user);
        firebaseAuthService.createFirebaseUser(user.getEmail());
        return savedUser;
    }

    private Language findOrCreateLanguage(String languageName) {
        Language language = languageRepository.findByLanguage(languageName);
        if (language == null) {
            language = languageRepository.save(new Language(languageName));
        }
        return language;
    }

    private Map<String, String> generateResponse(User savedUser) {
        String uid = firebaseAuthService.getUidUser(savedUser.getEmail());
        String customToken = firebaseAuthService.generateCustomToken(uid);
        Map tokens = firebaseAuthService.idTokenForLogin(customToken);
        UserTokenReponse userTokenReponse = new UserTokenReponse((String) tokens.get("idToken"), (String) tokens.get("refreshToken"), (String) tokens.get("expiresIn"));
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("idToken", userTokenReponse.getIdToken());
        response.put("refreshToken", userTokenReponse.getRefreshToken());
        response.put("expiresIn", userTokenReponse.getExpiresIn());
        return response;
    }

    private ResponseEntity<?> buildUserExistsResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User already exists, please login.");
        return ResponseEntity.badRequest().body(response);
    }
}
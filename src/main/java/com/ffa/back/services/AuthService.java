package com.ffa.back.services;


import com.ffa.back.dto.UserRequestRegister;
import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.dto.UserTokenReponse;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ResponseEntity<?> register(UserRequestRegister userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
        if (userOptional.isPresent()) {
            return buildUserExistsResponse();
        }

        try {
            // Crear usuario en Firebase
            Map<String, Object> firebaseResponse = (Map<String, Object>) firebaseAuthService.createFirebaseUser(userRequest.getEmail(), userRequest.getPassword());

            // Crear y guardar usuario en la base de datos
            User newUser = new User();
            newUser.setEmail(userRequest.getEmail());
            newUser.setProvider("email");
            Language language = findOrCreateLanguage(userRequest.getLanguage());
            newUser.setLanguage(language);

            userRepository.save(newUser);

            // Generar respuesta
            UserTokenReponse userTokenResponse = new UserTokenReponse(
                    (String) firebaseResponse.get("idToken"),
                    (String) firebaseResponse.get("refreshToken"),
                    (String) firebaseResponse.get("expiresIn")
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("idToken", userTokenResponse.getIdToken());
            response.put("refreshToken", userTokenResponse.getRefreshToken());
            response.put("expiresIn", userTokenResponse.getExpiresIn());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    public ResponseEntity<?> login(UserRequestRegister userRequest) {
        try {
            // Autenticar usuario en Firebase
            String uid = firebaseAuthService.getUidUser(userRequest.getEmail());
            String customToken = firebaseAuthService.generateCustomToken(uid);
            Map tokens = firebaseAuthService.idTokenForLogin(customToken);

            String idToken = (String) tokens.get("idToken");
            String refreshToken = (String) tokens.get("refreshToken");
            String expiresIn = (String) tokens.get("expiresIn");

            if (idToken == null || refreshToken == null || expiresIn == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve tokens");
            }

            UserTokenReponse userTokenResponse = new UserTokenReponse(idToken, refreshToken, expiresIn);

            return ResponseEntity.ok(userTokenResponse);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }


    private Language findOrCreateLanguage(String languageName) {
        if (languageName == null || languageName.isEmpty()) {
            languageName = "en"; // Idioma predeterminado
        }
        Language language = languageRepository.findByLanguage(languageName);
        if (language == null) {
            language = languageRepository.save(new Language(languageName));
        }
        return language;
    }

    private ResponseEntity<?> buildUserExistsResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User already exists, please login.");
        return ResponseEntity.badRequest().body(response);
    }
}

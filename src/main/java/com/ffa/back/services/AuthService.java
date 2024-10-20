package com.ffa.back.services;


import com.ffa.back.dto.UserRequestRegisterDTO;
import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.dto.UserTokenResponseDTO;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import com.google.firebase.auth.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public ResponseEntity<?> register(UserRequestRegisterDTO userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
        if (userOptional.isPresent()) {
            return buildUserExistsResponse();
        }

        try {
            // Crear usuario en Firebase
            UserRecord firebaseResponse = firebaseAuthService.createFirebaseUser(userRequest.getEmail(), userRequest.getPassword());
            // Obtenemos el Uid del Usuario
            String uidUser = firebaseAuthService.getUidUser(userRequest.getEmail());
            // Generamos el custom token
            String customToken = firebaseAuthService.generateCustomToken(uidUser);
            // Obtenemos el token del usuario
            Map tokens = firebaseAuthService.idTokenForLogin(customToken);


            // Crear y guardar usuario en la base de datos
            User newUser = new User();
            newUser.setEmail(userRequest.getEmail());
            newUser.setProvider("email");
            Language language = findOrCreateLanguage(userRequest.getLanguage());
            newUser.setLanguage(language);

            userRepository.save(newUser);

            // Generar respuesta
            UserTokenResponseDTO userTokenResponse = new UserTokenResponseDTO(
                    (String) tokens.get("idToken"),
                    (String) tokens.get("refreshToken"),
                    (String) tokens.get("expiresIn")
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

    public ResponseEntity<?> login(UserRequestRegisterDTO userRequest) {
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

            UserTokenResponseDTO userTokenResponse = new UserTokenResponseDTO(idToken, refreshToken, expiresIn);

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
        log.info("Language found: {}", language);
        if (language == null) {
            log.info("Aqui lo tiene que guardar en la base de datos");
            language = languageRepository.save(new Language(languageName));
            log.info("Aqui ha dejado de funcionar");
        }
        return language;
    }

    private ResponseEntity<?> buildUserExistsResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User already exists, please login.");
        return ResponseEntity.badRequest().body(response);
    }
}

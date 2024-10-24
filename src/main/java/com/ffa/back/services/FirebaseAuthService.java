package com.ffa.back.services;

import com.ffa.back.config.FirebaseProperties;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseAuthService {

    @Autowired
    private FirebaseProperties firebaseProperties;

    @Autowired
    private RestTemplate restTemplate;

    public UserRecord createFirebaseUser(String email, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setEmailVerified(true)
                    .setPassword(password);
            return FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Firebase error: " + e.getMessage());
        }
    }

    public String getUidUser(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return userRecord.getUid();
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Firebase error: " + e.getMessage());
        }
    }

    public String generateCustomToken(String uid) {
        try {
            return FirebaseAuth.getInstance().createCustomToken(uid);
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Firebase error: " + e.getMessage());
        }
    }

    public FirebaseToken verifyToken(String token) {
        try {
            String cleanedToken = token.replace("Bearer ", "");
            return FirebaseAuth.getInstance().verifyIdToken(cleanedToken);
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when verifying token: " + e.getMessage());
        }
    }

    public void deleteUserFirebase(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
        } catch (FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "An error occurred with the service: " + e.getMessage());
        }
    }

    public Map idTokenForLogin(String customTokenDecoded) {
        String url = firebaseProperties.getUrl() + "?key=" + firebaseProperties.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("token", customTokenDecoded);
        payload.put("returnSecureToken", true);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new ResponseStatusException(response.getStatusCode(), "Failed to get ID token");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during token retrieval: " + e.getMessage());
        }
    }
}

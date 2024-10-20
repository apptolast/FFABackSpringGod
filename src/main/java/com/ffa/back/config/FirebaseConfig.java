package com.ffa.back.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Autowired
    private FirebaseProperties firebaseProperties;

    @PostConstruct
    public void initializeFirebase() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(firebaseProperties.getApiFilename());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase has been initialized successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

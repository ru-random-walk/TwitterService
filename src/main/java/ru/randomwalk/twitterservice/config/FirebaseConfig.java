package ru.randomwalk.twitterservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile("!local")
public class FirebaseConfig {

    @Value("${firebase.credentials}")
    private String firebaseCredentials;

    private final ApplicationContext context;

    @PostConstruct
    public void initialize() {
        try {
            InputStream credentialsStream = new ByteArrayInputStream(firebaseCredentials.getBytes());

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error("Firebase initialization error:", e);
            SpringApplication.exit(context, () -> 1);
        }
    }
}
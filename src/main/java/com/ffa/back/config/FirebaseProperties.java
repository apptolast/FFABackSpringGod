package com.ffa.back.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {

    private String url;
    private String apiKey;
    private String pass;
    private String apiFilename;

}

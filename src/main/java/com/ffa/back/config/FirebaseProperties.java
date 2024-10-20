package com.ffa.back.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {

    private String url;
    private String apiKey;
    private String pass;
    private String apiFilename;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiFilename() {
        return apiFilename;
    }

    public void setApiFilename(String apiFilename) {
        this.apiFilename = apiFilename;
    }
}

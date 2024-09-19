package com.ffa.back.dto;

import jakarta.validation.constraints.Pattern;

public class UserUpdateRequestDTO {

    @Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code")
    private String language;

    private String email;

    // Constructor sin argumentos
    public UserUpdateRequestDTO() {
    }

    // Constructor con argumentos (opcional)
    public UserUpdateRequestDTO(String language, String email) {
        this.language = language;
        this.email = email;
    }

    // Getter y Setter
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

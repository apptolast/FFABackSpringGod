package com.ffa.back.dto;

import jakarta.validation.constraints.Pattern;

public class UserUpdateRequestDTO {

    @Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code")
    private String language;

    // Constructor sin argumentos
    public UserUpdateRequestDTO() {
    }

    // Constructor con argumentos (opcional)
    public UserUpdateRequestDTO(String language) {
        this.language = language;
    }

    // Getter y Setter
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

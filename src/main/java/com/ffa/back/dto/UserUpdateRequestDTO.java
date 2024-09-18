package com.ffa.back.dto;

import jakarta.validation.constraints.Pattern;

public class UserUpdateRequestDTO {

    @Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code")
    private String language;

    public UserUpdateRequestDTO(String language) {
        this.language = language;
    }

    public @Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code") String getLanguage() {
        return language;
    }

    public void setLanguage(@Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code") String language) {
        this.language = language;
    }

    // Getters y setters
}

package com.ffa.back.dto;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String provider;
    private String language;

    // Constructor, getters y setters

    public UserResponseDTO() {
    }


    public UserResponseDTO(Long id, String email, String provider, String language) {
        this.id = id;
        this.email = email;
        this.provider = provider;
        this.language = language;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

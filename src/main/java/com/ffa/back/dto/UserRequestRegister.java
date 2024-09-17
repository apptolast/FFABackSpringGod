package com.ffa.back.dto;

public class UserRequestRegister {

    private String email;

    private String password;

    private String language;

    public UserRequestRegister() {}

    public UserRequestRegister(String email, String password, String language) {
        this.email = email;
        this.password = password;
        this.language = language;
    }

    // Getters y Setters


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

package com.ffa.back.dto;

import java.util.List;

public class LanguageDTO {
    private Long id;
    private String language;
    private List<UserDTO> users;

    // Constructores
    public LanguageDTO() {
    }

    public LanguageDTO(Long id, String language, List<UserDTO> users) {
        this.id = id;
        this.language = language;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}

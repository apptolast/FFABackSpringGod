package com.ffa.back.dto;

import java.util.List;

public class LanguageDTO {
    private Long id;
    private String language;
    private List<Long> userIds;

    // Constructores
    public LanguageDTO() {
    }

    public LanguageDTO(Long id, String language, List<Long> userIds) {
        this.id = id;
        this.language = language;
        this.userIds = userIds;
    }

    // Getters y Setters
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}

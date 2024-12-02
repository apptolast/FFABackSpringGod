package com.ffa.back.dto;

import com.ffa.back.models.Language;

public class LanguageDTO {
    private Long id;
    private String language;

    public LanguageDTO() {
    }

    public LanguageDTO(Language language) {
        this.id = language.getId();
        this.language = language.getLanguage();
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
}

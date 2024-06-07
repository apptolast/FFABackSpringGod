package com.ffa.back.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String language;

    @OneToMany(mappedBy = "language")
    private List<User> users;

    protected Language() {}

    public Language(String language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public List<User> getUsers() {
        return users;
    }
}

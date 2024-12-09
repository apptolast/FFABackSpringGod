package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String language;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<User> users = new ArrayList<>();

    public Language() {
    }

    public Language(String language) {
        this.language = language;
    }

    public void setLanguage(String language) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

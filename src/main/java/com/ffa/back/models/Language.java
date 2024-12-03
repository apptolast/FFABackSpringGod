package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<User> users;

    // Constructores
    protected Language() {}

    public Language(Long id, String language, List<User> users) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

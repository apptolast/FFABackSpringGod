package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_language", referencedColumnName = "id")
    @JsonBackReference
    private Language language;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = true)
    private String firebase_uuid;

    @OneToMany(mappedBy = "owner")
    private List<Group> groups;

    @Column(nullable = true)
    private String role;

    @OneToMany(mappedBy = "user")
    private List<GroupUser> groupUsers;

    @OneToMany(mappedBy = "user")
    private List<MovieUserGroup> movieUserGroups;

    public User() {}

    public User(String role, String firebase_uuid, String provider, String email) {
        this.role = role;
        this.firebase_uuid = firebase_uuid;
        this.provider = provider;
        this.email = email;
    }


    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public List<MovieUserGroup> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroup> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public String getProvider() {
        return provider;
    }

    public String getFirebase_uuid() {
        return firebase_uuid;
    }

    public String getRole() {
        return role;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setFirebase_uuid(String firebase_uuid) {
        this.firebase_uuid = firebase_uuid;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

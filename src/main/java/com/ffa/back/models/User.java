package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "firebase_uuid")
    private String firebaseUuid;

    @Column(nullable = false)
    private String provider;

    @Column
    private String role;

    @Column(name = "auth_time")
    private Long authTime;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "expiry_time")
    private Long expiryTime;

    @Column(name = "issue_time")
    private Long issueTime;

    @Column(name = "identity_provider")
    private String identityProvider;

    @Column(nullable = false)
    private String sub;

    @ManyToOne
    @JoinColumn(name = "id_language")
    private Language language;

    @OneToMany(mappedBy = "owner")
    private List<Group> ownedGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_viewed_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> viewedMovies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_to_watch_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> toWatchMovies = new ArrayList<>();

    public User(Long id, String email, String firebaseUuid, String provider, String role, Long authTime, Boolean emailVerified, Long expiryTime, Long issueTime, String identityProvider, String sub, Language language, List<Group> ownedGroups, List<MovieUserGroup> movieUserGroups, List<Movie> viewedMovies, List<Movie> toWatchMovies) {
        this.id = id;
        this.email = email;
        this.firebaseUuid = firebaseUuid;
        this.provider = provider;
        this.role = role;
        this.authTime = authTime;
        this.emailVerified = emailVerified;
        this.expiryTime = expiryTime;
        this.issueTime = issueTime;
        this.identityProvider = identityProvider;
        this.sub = sub;
        this.language = language;
        this.ownedGroups = ownedGroups;
        this.movieUserGroups = movieUserGroups;
        this.viewedMovies = viewedMovies;
        this.toWatchMovies = toWatchMovies;
    }

    public User() {
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

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Long authTime) {
        this.authTime = authTime;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Long issueTime) {
        this.issueTime = issueTime;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Group> getOwnedGroups() {
        return ownedGroups;
    }

    public void setOwnedGroups(List<Group> ownedGroups) {
        this.ownedGroups = ownedGroups;
    }

    public List<MovieUserGroup> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroup> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
    }

    public List<Movie> getViewedMovies() {
        return viewedMovies;
    }

    public void setViewedMovies(List<Movie> viewedMovies) {
        this.viewedMovies = viewedMovies;
    }

    public List<Movie> getToWatchMovies() {
        return toWatchMovies;
    }

    public void setToWatchMovies(List<Movie> toWatchMovies) {
        this.toWatchMovies = toWatchMovies;
    }
}
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

    // Campos de autenticación Firebase
    @Column(name = "firebase_uuid", nullable = false)
    private String firebaseUuid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = true)
    private String role;

    // Campos del token JWT
    @Column(nullable = false)
    private String sub;  // subject id from token

    @Column(name = "auth_time")
    private Long authTime;

    @Column(name = "issue_time")
    private Long iat;

    @Column(name = "expiry_time")
    private Long exp;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "identity_provider")
    private String signInProvider;

    // Relación con Language
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_language", referencedColumnName = "id")
    @JsonBackReference
    private Language language;

    // NUEVOS CAMPOS
    // Relación con grupos a través de GroupUser
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups = new ArrayList<>();


    // Películas vistas
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_viewed_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> vistas = new ArrayList<>();


    // Películas por ver
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_to_watch_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> porVer = new ArrayList<>();


    public User() {
    }

    public User(Long id, String firebaseUuid, String email, String provider, String role, String sub, Long authTime, Long iat, Long exp, Boolean emailVerified, String signInProvider, Language language, List<Group> groups, List<Movie> vistas, List<Movie> porVer) {
        this.id = id;
        this.firebaseUuid = firebaseUuid;
        this.email = email;
        this.provider = provider;
        this.role = role;
        this.sub = sub;
        this.authTime = authTime;
        this.iat = iat;
        this.exp = exp;
        this.emailVerified = emailVerified;
        this.signInProvider = signInProvider;
        this.language = language;
        this.groups = groups;
        this.vistas = vistas;
        this.porVer = porVer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Long authTime) {
        this.authTime = authTime;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(String signInProvider) {
        this.signInProvider = signInProvider;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Movie> getVistas() {
        return vistas;
    }

    public void setVistas(List<Movie> vistas) {
        this.vistas = vistas;
    }

    public List<Movie> getPorVer() {
        return porVer;
    }

    public void setPorVer(List<Movie> porVer) {
        this.porVer = porVer;
    }
}
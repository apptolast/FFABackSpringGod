package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

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
  
    @Column(name = "firebase_uuid", nullable = true)
    private String firebaseUuid;

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

    // Constructores
    public User() {}

    public User(String email, String firebaseUuid, String provider, String role) {
        this.email = email;
    }
    public User(String role, String firebaseUuid, String provider, String email) {
        this.role = role;
        this.firebaseUuid = firebaseUuid;
        this.provider = provider;
        this.role = role;
    }

    // Getters y Setters
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

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;

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
}
package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    // Grupos donde el usuario es propietario
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Group> ownedGroups;

    // Grupos donde el usuario es miembro
    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    // Películas vistas
    @ManyToMany
    @JoinTable(
            name = "user_viewed_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> viewedMovies;

    // Películas por ver
    @ManyToMany
    @JoinTable(
            name = "user_watchlist_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> watchlistMovies;

    // Constructores
    public User() {}

    public User(Long id, String firebaseUuid, String email, String provider, String role, String sub,
                Long authTime, Long iat, Long exp, Boolean emailVerified, String signInProvider,
                Language language, List<Group> ownedGroups, List<Group> groups,
                List<Movie> viewedMovies, List<Movie> watchlistMovies) {
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
        this.ownedGroups = ownedGroups;
        this.groups = groups;
        this.viewedMovies = viewedMovies;
        this.watchlistMovies = watchlistMovies;
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

    public List<Group> getOwnedGroups() {
        return ownedGroups;
    }

    public void setOwnedGroups(List<Group> ownedGroups) {
        this.ownedGroups = ownedGroups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Movie> getViewedMovies() {
        return viewedMovies;
    }

    public void setViewedMovies(List<Movie> viewedMovies) {
        this.viewedMovies = viewedMovies;
    }

    public List<Movie> getWatchlistMovies() {
        return watchlistMovies;
    }

    public void setWatchlistMovies(List<Movie> watchlistMovies) {
        this.watchlistMovies = watchlistMovies;
    }
}
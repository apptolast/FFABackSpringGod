package com.ffa.back.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String firebaseUuid;
    private String email;
    private String provider;
    private String role;
    private String sub;
    private Long authTime;
    private Long iat;
    private Long exp;
    private Boolean emailVerified;
    private String signInProvider;
    private LanguageDTO language;
    private List<GroupDTO> ownedGroups;
    private List<GroupDTO> groups;
    private List<MovieDTO> viewedMovies;
    private List<MovieDTO> watchlistMovies;

    // Constructores
    public UserDTO() {
    }

    public UserDTO(Long id, String firebaseUuid, String email, String provider, String role, String sub,
                   Long authTime, Long iat, Long exp, Boolean emailVerified, String signInProvider,
                   LanguageDTO language, List<GroupDTO> ownedGroups, List<GroupDTO> groups,
                   List<MovieDTO> viewedMovies, List<MovieDTO> watchlistMovies) {
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

    public LanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDTO language) {
        this.language = language;
    }

    public List<GroupDTO> getOwnedGroups() {
        return ownedGroups;
    }

    public void setOwnedGroups(List<GroupDTO> ownedGroups) {
        this.ownedGroups = ownedGroups;
    }

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

    public List<MovieDTO> getViewedMovies() {
        return viewedMovies;
    }

    public void setViewedMovies(List<MovieDTO> viewedMovies) {
        this.viewedMovies = viewedMovies;
    }

    public List<MovieDTO> getWatchlistMovies() {
        return watchlistMovies;
    }

    public void setWatchlistMovies(List<MovieDTO> watchlistMovies) {
        this.watchlistMovies = watchlistMovies;
    }
}

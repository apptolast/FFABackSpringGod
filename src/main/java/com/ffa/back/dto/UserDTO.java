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
    private Long languageId;
    private List<Long> ownedGroupIds;
    private List<Long> groupIds;
    private List<Long> viewedMovieIds;
    private List<Long> watchlistMovieIds;

    // Constructores
    public UserDTO() {
    }

    public UserDTO(Long id, String firebaseUuid, String email, String provider, String role, String sub,
                   Long authTime, Long iat, Long exp, Boolean emailVerified, String signInProvider,
                   Long languageId, List<Long> ownedGroupIds, List<Long> groupIds,
                   List<Long> viewedMovieIds, List<Long> watchlistMovieIds) {
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
        this.languageId = languageId;
        this.ownedGroupIds = ownedGroupIds;
        this.groupIds = groupIds;
        this.viewedMovieIds = viewedMovieIds;
        this.watchlistMovieIds = watchlistMovieIds;
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

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public List<Long> getOwnedGroupIds() {
        return ownedGroupIds;
    }

    public void setOwnedGroupIds(List<Long> ownedGroupIds) {
        this.ownedGroupIds = ownedGroupIds;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public List<Long> getViewedMovieIds() {
        return viewedMovieIds;
    }

    public void setViewedMovieIds(List<Long> viewedMovieIds) {
        this.viewedMovieIds = viewedMovieIds;
    }

    public List<Long> getWatchlistMovieIds() {
        return watchlistMovieIds;
    }

    public void setWatchlistMovieIds(List<Long> watchlistMovieIds) {
        this.watchlistMovieIds = watchlistMovieIds;
    }
}

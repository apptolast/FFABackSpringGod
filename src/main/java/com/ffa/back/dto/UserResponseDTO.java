package com.ffa.back.dto;

import java.util.List;

public class UserResponseDTO {

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
    private String language; // Mantenemos el language como String para simplicidad
    private List<MovieResponseDTO> vistas;
    private List<MovieResponseDTO> porVer;
    private List<Long> groupIds;

    // Constructor vacío
    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String firebaseUuid, String email, String provider, String role, String sub, Long authTime, Long iat, Long exp, Boolean emailVerified, String signInProvider, String language, List<MovieResponseDTO> vistas, List<MovieResponseDTO> porVer, List<Long> groupIds) {
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
        this.vistas = vistas;
        this.porVer = porVer;
        this.groupIds = groupIds;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<MovieResponseDTO> getVistas() {
        return vistas;
    }

    public void setVistas(List<MovieResponseDTO> vistas) {
        this.vistas = vistas;
    }

    public List<MovieResponseDTO> getPorVer() {
        return porVer;
    }

    public void setPorVer(List<MovieResponseDTO> porVer) {
        this.porVer = porVer;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }
}

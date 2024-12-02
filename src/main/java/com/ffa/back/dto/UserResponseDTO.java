package com.ffa.back.dto;

import com.ffa.back.models.User;

import java.util.List;
import java.util.stream.Collectors;

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
    private LanguageDTO language;
    private List<GroupUserDTO> groupUsers;
    private List<GroupDTO> ownedGroups;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firebaseUuid = user.getFirebaseUuid();
        this.email = user.getEmail();
        this.provider = user.getProvider();
        this.role = user.getRole();
        this.sub = user.getSub();
        this.authTime = user.getAuthTime();
        this.iat = user.getIat();
        this.exp = user.getExp();
        this.emailVerified = user.getEmailVerified();
        this.signInProvider = user.getSignInProvider();
        this.language = (user.getLanguage() != null) ? new LanguageDTO(user.getLanguage()) : null;
        this.groupUsers = (user.getGroupUsers() != null) ? user.getGroupUsers().stream().map(GroupUserDTO::new).collect(Collectors.toList()) : null;
        this.ownedGroups = (user.getOwnedGroups() != null) ? user.getOwnedGroups().stream().map(GroupDTO::new).collect(Collectors.toList()) : null;
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

    public List<GroupUserDTO> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUserDTO> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public List<GroupDTO> getOwnedGroups() {
        return ownedGroups;
    }

    public void setOwnedGroups(List<GroupDTO> ownedGroups) {
        this.ownedGroups = ownedGroups;
    }
}

package com.ffa.back.dto;

public class UserTokenReponse {

    private String idToken;
    private String refreshToken;
    private String expiresIn;

    protected UserTokenReponse() {}

    public UserTokenReponse(String idToken, String refreshToken, String expiresIn) {
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}

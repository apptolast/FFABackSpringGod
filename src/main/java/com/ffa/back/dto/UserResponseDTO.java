package com.ffa.back.dto;

import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String language; // Mantenemos el language como String para simplicidad
    private List<MovieResponseDTO> watchedMovies;
    private List<MovieResponseDTO> toWatchMovies;
    private List<Long> joinedGroupIds;

    // Constructor vacío
    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String email, String language, List<MovieResponseDTO> watchedMovies, List<MovieResponseDTO> toWatchMovies, List<Long> joinedGroupIds) {
        this.id = id;
        this.email = email;
        this.language = language;
        this.watchedMovies = watchedMovies;
        this.toWatchMovies = toWatchMovies;
        this.joinedGroupIds = joinedGroupIds;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<MovieResponseDTO> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(List<MovieResponseDTO> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public List<MovieResponseDTO> getToWatchMovies() {
        return toWatchMovies;
    }

    public void setToWatchMovies(List<MovieResponseDTO> toWatchMovies) {
        this.toWatchMovies = toWatchMovies;
    }

    public List<Long> getJoinedGroupIds() {
        return joinedGroupIds;
    }

    public void setJoinedGroupIds(List<Long> joinedGroupIds) {
        this.joinedGroupIds = joinedGroupIds;
    }
}

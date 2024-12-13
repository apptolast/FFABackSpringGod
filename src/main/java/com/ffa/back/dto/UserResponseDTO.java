package com.ffa.back.dto;

import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String language; // Mantenemos el language como String para simplicidad
    private List<MovieResponseDTO> seenMoviesUser;
    private List<MovieResponseDTO> toSeeMoviesUser;
    private List<Long> joinedGroupsIds;

    // Constructor vacío
    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String email, String language, List<MovieResponseDTO> seenMoviesUser, List<MovieResponseDTO> toSeeMoviesUser, List<Long> joinedGroupsIds) {
        this.id = id;
        this.email = email;
        this.language = language;
        this.seenMoviesUser = seenMoviesUser;
        this.toSeeMoviesUser = toSeeMoviesUser;
        this.joinedGroupsIds = joinedGroupsIds;
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

    public List<MovieResponseDTO> getSeenMoviesUser() {
        return seenMoviesUser;
    }

    public void setSeenMoviesUser(List<MovieResponseDTO> seenMoviesUser) {
        this.seenMoviesUser = seenMoviesUser;
    }

    public List<MovieResponseDTO> getToSeeMoviesUser() {
        return toSeeMoviesUser;
    }

    public void setToSeeMoviesUser(List<MovieResponseDTO> toSeeMoviesUser) {
        this.toSeeMoviesUser = toSeeMoviesUser;
    }

    public List<Long> getJoinedGroupsIds() {
        return joinedGroupsIds;
    }

    public void setJoinedGroupsIds(List<Long> joinedGroupsIds) {
        this.joinedGroupsIds = joinedGroupsIds;
    }
}

package com.ffa.back.dto;

public class MovieUserGroupIdDTO {
    private Long movieId;
    private Long userId;
    private Long groupId;

    // Constructores
    public MovieUserGroupIdDTO() {
    }

    public MovieUserGroupIdDTO(Long movieId, Long userId, Long groupId) {
        this.movieId = movieId;
        this.userId = userId;
        this.groupId = groupId;
    }

    // Getters y Setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}

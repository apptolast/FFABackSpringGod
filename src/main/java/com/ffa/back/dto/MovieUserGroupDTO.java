package com.ffa.back.dto;

public class MovieUserGroupDTO {
    private MovieUserGroupIdDTO id;
    private Long movieId;
    private Long userId;
    private Long groupId;
    private Boolean toWatch;

    // Constructores
    public MovieUserGroupDTO() {
    }

    public MovieUserGroupDTO(MovieUserGroupIdDTO id, Long movieId, Long userId,
                             Long groupId, Boolean toWatch) {
        this.id = id;
        this.movieId = movieId;
        this.userId = userId;
        this.groupId = groupId;
        this.toWatch = toWatch;
    }

    // Getters y Setters
    public MovieUserGroupIdDTO getId() {
        return id;
    }

    public void setId(MovieUserGroupIdDTO id) {
        this.id = id;
    }

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

    public Boolean getToWatch() {
        return toWatch;
    }

    public void setToWatch(Boolean toWatch) {
        this.toWatch = toWatch;
    }
}

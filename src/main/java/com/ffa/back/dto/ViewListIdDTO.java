package com.ffa.back.dto;

public class ViewListIdDTO {
    private Long groupId;
    private Long movieId;

    // Constructores
    public ViewListIdDTO() {
    }

    public ViewListIdDTO(Long groupId, Long movieId) {
        this.groupId = groupId;
        this.movieId = movieId;
    }

    // Getters y Setters
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}

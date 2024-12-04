package com.ffa.back.dto;

public class ViewListDTO {
    private ViewListIdDTO id;
    private Long groupId;
    private Long movieId;

    // Constructores
    public ViewListDTO() {
    }

    public ViewListDTO(ViewListIdDTO id, Long groupId, Long movieId) {
        this.id = id;
        this.groupId = groupId;
        this.movieId = movieId;
    }

    // Getters y Setters
    public ViewListIdDTO getId() {
        return id;
    }

    public void setId(ViewListIdDTO id) {
        this.id = id;
    }

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

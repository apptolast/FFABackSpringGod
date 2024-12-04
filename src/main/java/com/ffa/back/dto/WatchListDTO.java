package com.ffa.back.dto;

public class WatchListDTO {
    private WatchListIdDTO id;
    private Long groupId;
    private Long movieId;

    // Constructores
    public WatchListDTO() {
    }

    public WatchListDTO(WatchListIdDTO id, Long groupId, Long movieId) {
        this.id = id;
        this.groupId = groupId;
        this.movieId = movieId;
    }

    // Getters y Setters
    public WatchListIdDTO getId() {
        return id;
    }

    public void setId(WatchListIdDTO id) {
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

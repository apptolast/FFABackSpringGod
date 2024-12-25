package com.ffa.back.dto;

import java.util.List;

public class AddMovieToGroupRequestDTO {
    private Long movieId;
    private Long groupId;
    private boolean toWatch; // true=para ver false = vistas
    private boolean addMovie; // true = por ver, false = vistas

    public AddMovieToGroupRequestDTO(Long movieId, Long groupId, boolean toWatch, boolean addMovie) {
        this.movieId = movieId;
        this.groupId = groupId;
        this.toWatch = toWatch;
        this.addMovie = addMovie;
    }

    public AddMovieToGroupRequestDTO() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public boolean isToWatch() {
        return toWatch;
    }

    public void setToWatch(boolean toWatch) {
        this.toWatch = toWatch;
    }

    public boolean isAddMovie() {
        return addMovie;
    }

    public void setAddMovie(boolean addMovie) {
        this.addMovie = addMovie;
    }
}
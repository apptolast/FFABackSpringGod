package com.ffa.back.dto;

import java.util.List;

public class GenreDTO {

    private Long id;
    private String name;
    private List<Long> movieIds;

    // Constructores
    public GenreDTO() {
    }

    public GenreDTO(Long id, String name, List<Long> movieIds) {
        this.id = id;
        this.name = name;
        this.movieIds = movieIds;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
        this.movieIds = movieIds;
    }
}

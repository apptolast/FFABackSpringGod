package com.ffa.back.dto;

import java.util.List;

public class GenreDTO {

    private Long id;
    private String name;
    private List<MovieDTO> movies;

    // Constructores
    public GenreDTO() {
    }

    public GenreDTO(Long id, String name, List<MovieDTO> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

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

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}

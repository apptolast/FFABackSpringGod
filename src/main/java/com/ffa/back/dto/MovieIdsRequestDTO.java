package com.ffa.back.dto;

import java.util.List;

public class MovieIdsRequestDTO {

    private List<Long> movieIds;


    public MovieIdsRequestDTO(List<Long> movieIds) {
        this.movieIds = movieIds;
    }

    public MovieIdsRequestDTO() {
    }

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
        this.movieIds = movieIds;
    }
}

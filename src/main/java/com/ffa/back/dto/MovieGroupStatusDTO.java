package com.ffa.back.dto;

import java.util.List;

public class MovieGroupStatusDTO {

    private Long movieId;
    private List<GroupMovieStatusDTO> groups;

    public MovieGroupStatusDTO(Long movieId, List<GroupMovieStatusDTO> groups) {
        this.movieId = movieId;
        this.groups = groups;
    }

    public MovieGroupStatusDTO() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public List<GroupMovieStatusDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupMovieStatusDTO> groups) {
        this.groups = groups;
    }
}

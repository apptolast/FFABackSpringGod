package com.ffa.back.dto;

import java.util.List;

public class MovieResponseDTO {
    private Long movieId;
    private String title;
    private List<Long> groupIds;

    public MovieResponseDTO() {
    }

    public MovieResponseDTO(Long movieId, String title, List<Long> groupIds) {
        this.movieId = movieId;
        this.title = title;
        this.groupIds = groupIds;
    }

    public MovieResponseDTO(List<Long> groupIds, Long movieId) {
        this.groupIds = groupIds;
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }
}

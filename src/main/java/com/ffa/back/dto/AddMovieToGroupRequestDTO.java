package com.ffa.back.dto;

import java.util.List;

public class AddMovieToGroupRequestDTO {
    private Long movieId;
    private List<Long> groupIds;
    private boolean toWatch; // true = por ver, false = vistas

    public AddMovieToGroupRequestDTO() {
    }

    public AddMovieToGroupRequestDTO(Long movieId, List<Long> groupIds, boolean toWatch) {
        this.movieId = movieId;
        this.groupIds = groupIds;
        this.toWatch = toWatch;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public boolean isToWatch() {
        return toWatch;
    }

    public void setToWatch(boolean toWatch) {
        this.toWatch = toWatch;
    }
}
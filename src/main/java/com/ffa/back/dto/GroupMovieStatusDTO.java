package com.ffa.back.dto;

import com.ffa.back.enums.MovieGroupStatus;

public class GroupMovieStatusDTO {

    private Long groupId;
    private MovieGroupStatus status;


    public GroupMovieStatusDTO(Long groupId, MovieGroupStatus status) {
        this.groupId = groupId;
        this.status = status;
    }

    public GroupMovieStatusDTO() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public MovieGroupStatus getStatus() {
        return status;
    }

    public void setStatus(MovieGroupStatus status) {
        this.status = status;
    }
}

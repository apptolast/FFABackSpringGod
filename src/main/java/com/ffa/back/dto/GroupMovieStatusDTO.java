package com.ffa.back.dto;

import com.ffa.back.enums.MovieGroupStatus;

public class GroupMovieStatusDTO {

    private Long groupId;
    private MovieGroupStatus status;
    private String groupName;

    public GroupMovieStatusDTO(Long groupId, MovieGroupStatus status, String groupName) {
        this.groupId = groupId;
        this.status = status;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

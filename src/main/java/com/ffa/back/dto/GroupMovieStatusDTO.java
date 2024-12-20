package com.ffa.back.dto;

import com.ffa.back.enums.MovieGroupStatus;

public class GroupMovieStatusDTO {

    private Long groupId;
    private String groupName;
    private MovieGroupStatus status;

    public GroupMovieStatusDTO(Long groupId, String groupName, MovieGroupStatus status) {
        this.groupId = groupId;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public MovieGroupStatus getStatus() {
        return status;
    }

    public void setStatus(MovieGroupStatus status) {
        this.status = status;
    }
}

package com.ffa.back.dto;

import java.util.List;

public class GroupDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private List<Long> memberIds;
    private List<Long> watchListIds;
    private List<Long> viewListIds;
    private List<Long> movieUserGroupIds;

    // Constructores
    public GroupDTO() {
    }

    public GroupDTO(Long id, Long ownerId, String name, List<Long> memberIds,
                    List<Long> watchListIds, List<Long> viewListIds,
                    List<Long> movieUserGroupIds) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.memberIds = memberIds;
        this.watchListIds = watchListIds;
        this.viewListIds = viewListIds;
        this.movieUserGroupIds = movieUserGroupIds;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }

    public List<Long> getWatchListIds() {
        return watchListIds;
    }

    public void setWatchListIds(List<Long> watchListIds) {
        this.watchListIds = watchListIds;
    }

    public List<Long> getViewListIds() {
        return viewListIds;
    }

    public void setViewListIds(List<Long> viewListIds) {
        this.viewListIds = viewListIds;
    }

    public List<Long> getMovieUserGroupIds() {
        return movieUserGroupIds;
    }

    public void setMovieUserGroupIds(List<Long> movieUserGroupIds) {
        this.movieUserGroupIds = movieUserGroupIds;
    }
}

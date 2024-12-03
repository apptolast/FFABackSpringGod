package com.ffa.back.dto;

import java.util.List;

public class GroupDTO {
    private Long id;
    private UserDTO owner;
    private String name;
    private List<UserDTO> members;
    private List<WatchListDTO> watchLists;
    private List<ViewListDTO> viewLists;
    private List<MovieUserGroupDTO> movieUserGroups;

    // Constructores
    public GroupDTO() {
    }

    public GroupDTO(Long id, UserDTO owner, String name, List<UserDTO> members,
                    List<WatchListDTO> watchLists, List<ViewListDTO> viewLists,
                    List<MovieUserGroupDTO> movieUserGroups) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.members = members;
        this.watchLists = watchLists;
        this.viewLists = viewLists;
        this.movieUserGroups = movieUserGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }

    public List<WatchListDTO> getWatchLists() {
        return watchLists;
    }

    public void setWatchLists(List<WatchListDTO> watchLists) {
        this.watchLists = watchLists;
    }

    public List<ViewListDTO> getViewLists() {
        return viewLists;
    }

    public void setViewLists(List<ViewListDTO> viewLists) {
        this.viewLists = viewLists;
    }

    public List<MovieUserGroupDTO> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroupDTO> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
    }
}

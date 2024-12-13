package com.ffa.back.dto;

import java.util.List;

public class GroupResponseDTO {


    private Long id;
    private Long ownerId;
    private String name;
    private List<UserResponseDTO> users;
    private List<MovieUsersDTO> watched;
    private List<MovieUsersDTO> toWatch;

    public GroupResponseDTO(Long id, Long ownerId, String name,
                            List<UserResponseDTO> users,
                            List<MovieUsersDTO> watched,
                            List<MovieUsersDTO> toWatch) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.users = users;
        this.watched = watched;
        this.toWatch = toWatch;
    }

    public GroupResponseDTO() {
    }

    // Getters y setters
    public List<MovieUsersDTO> getWatched() {
        return watched;
    }

    public void setWatched(List<MovieUsersDTO> watched) {
        this.watched = watched;
    }

    public List<MovieUsersDTO> getToWatch() {
        return toWatch;
    }

    public void setToWatch(List<MovieUsersDTO> toWatch) {
        this.toWatch = toWatch;
    }

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

    public List<UserResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDTO> users) {
        this.users = users;
    }
}

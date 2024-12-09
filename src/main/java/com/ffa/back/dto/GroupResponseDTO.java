package com.ffa.back.dto;

import java.util.List;

public class GroupResponseDTO {


    private Long id;


    private Long ownerId;


    private String name;


    private List<UserResponseDTO> users;


    public GroupResponseDTO() {
    }

    public GroupResponseDTO(Long id, Long ownerId, String name, List<UserResponseDTO> users) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.users = users;
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

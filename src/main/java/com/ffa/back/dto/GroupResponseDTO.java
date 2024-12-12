package com.ffa.back.dto;

import java.util.List;

public class GroupResponseDTO {


    private Long id;
    private Long ownerId;
    private String name;
    private List<UserResponseDTO> users;
    private List<MovieUserDTO> vistas;
    private List<MovieUserDTO> porVer;

    public GroupResponseDTO() {
    }

    public GroupResponseDTO(Long id, Long ownerId, String name, List<UserResponseDTO> users,
                            List<MovieUserDTO> vistas, List<MovieUserDTO> porVer) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.users = users;
        this.vistas = vistas;
        this.porVer = porVer;
    }

    // Getters y setters existentes...

    public List<MovieUserDTO> getVistas() {
        return vistas;
    }

    public void setVistas(List<MovieUserDTO> vistas) {
        this.vistas = vistas;
    }

    public List<MovieUserDTO> getPorVer() {
        return porVer;
    }

    public void setPorVer(List<MovieUserDTO> porVer) {
        this.porVer = porVer;
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

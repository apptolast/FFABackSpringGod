package com.ffa.back.dto;

import com.ffa.back.models.Group;

public class GroupDTO {
    private Long id;
    private String name;
    private Long ownerId;

    public GroupDTO() {
    }

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.ownerId = (group.getOwner() != null) ? group.getOwner().getId() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

package com.ffa.back.dto;

public class GroupUserDTO {
    private GroupUserIdDTO id;
    private UserDTO user;
    private GroupDTO group;

    // Constructores
    public GroupUserDTO() {
    }

    public GroupUserDTO(GroupUserIdDTO id, UserDTO user, GroupDTO group) {
        this.id = id;
        this.user = user;
        this.group = group;
    }

    public GroupUserIdDTO getId() {
        return id;
    }

    public void setId(GroupUserIdDTO id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }
}

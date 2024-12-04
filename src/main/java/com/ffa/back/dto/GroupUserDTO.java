package com.ffa.back.dto;

public class GroupUserDTO {
    private GroupUserIdDTO id;
    private Long userId;
    private Long groupId;

    // Constructores
    public GroupUserDTO() {
    }

    public GroupUserDTO(GroupUserIdDTO id, Long userId, Long groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    // Getters y Setters
    public GroupUserIdDTO getId() {
        return id;
    }

    public void setId(GroupUserIdDTO id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}

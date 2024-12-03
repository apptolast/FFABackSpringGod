package com.ffa.back.dto;

public class GroupUserIdDTO {
    private Long userId;
    private Long groupId;

    // Constructores
    public GroupUserIdDTO() {
    }

    public GroupUserIdDTO(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
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

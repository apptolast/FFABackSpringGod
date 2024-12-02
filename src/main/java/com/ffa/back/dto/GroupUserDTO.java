package com.ffa.back.dto;

import com.ffa.back.models.GroupUser;

public class GroupUserDTO {
    private Long userId;
    private Long groupId;

    public GroupUserDTO() {
    }

    public GroupUserDTO(GroupUser groupUser) {
        this.userId = groupUser.getUser().getId();
        this.groupId = groupUser.getGroup().getId();
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

package com.ffa.back.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupUserId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    // Constructores
    public GroupUserId() {
    }

    public GroupUserId(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    // Getters y Setters

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


    // Equals y hashCode
    // ... (implementación de equals y hashCode)


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GroupUserId that = (GroupUserId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }
}

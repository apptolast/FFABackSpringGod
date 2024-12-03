package com.ffa.back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "group_users")
public class GroupUser {

    @EmbeddedId
    private GroupUserId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    // Constructores
    protected GroupUser() {}

    public GroupUser(GroupUserId id, User user, Group group) {
        this.id = id;
        this.user = user;
        this.group = group;
    }

    public GroupUserId getId() {
        return id;
    }

    public void setId(GroupUserId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

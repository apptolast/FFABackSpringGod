package com.ffa.back.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    private List<GroupUser> groupUsers;

    @OneToMany(mappedBy = "group")
    private List<WatchList> watchLists;

    @OneToMany(mappedBy = "group")
    private List<ViewList> viewLists;

    @OneToMany(mappedBy = "group")
    private List<MovieUserGroup> movieUserGroups;

    protected Group() {}

    public Group(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public List<WatchList> getWatchLists() {
        return watchLists;
    }

    public void setWatchLists(List<WatchList> watchLists) {
        this.watchLists = watchLists;
    }

    public List<ViewList> getViewLists() {
        return viewLists;
    }

    public void setViewLists(List<ViewList> viewLists) {
        this.viewLists = viewLists;
    }

    public List<MovieUserGroup> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroup> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

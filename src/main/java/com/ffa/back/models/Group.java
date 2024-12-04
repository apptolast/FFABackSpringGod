package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Propietario del grupo
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(nullable = false)
    private String name;

    // Miembros del grupo
    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    // Otros campos y relaciones
    @OneToMany(mappedBy = "group")
    private List<WatchList> watchLists;

    @OneToMany(mappedBy = "group")
    private List<ViewList> viewLists;

    @OneToMany(mappedBy = "group")
    private List<MovieUserGroup> movieUserGroups;

    // Constructores
    public Group() {
    }

    public Group(Long id, User owner, String name, List<User> members,
                 List<WatchList> watchLists, List<ViewList> viewLists,
                 List<MovieUserGroup> movieUserGroups) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.members = members;
        this.watchLists = watchLists;
        this.viewLists = viewLists;
        this.movieUserGroups = movieUserGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
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
}

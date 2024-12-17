package com.ffa.back.models;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupUser> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<WatchList> watchLists = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<ViewList> viewLists = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_movie_id")
    private Movie recommendedMovie;

    public Group() {
    }

    public Group(Long id, User owner, String name, List<GroupUser> groupUsers, List<WatchList> watchLists, List<ViewList> viewLists, List<MovieUserGroup> movieUserGroups, Movie recommendedMovie) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.groupUsers = groupUsers;
        this.watchLists = watchLists;
        this.viewLists = viewLists;
        this.movieUserGroups = movieUserGroups;
        this.recommendedMovie = recommendedMovie;
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

    public Movie getRecommendedMovie() {
        return recommendedMovie;
    }

    public void setRecommendedMovie(Movie recommendedMovie) {
        this.recommendedMovie = recommendedMovie;
    }
}

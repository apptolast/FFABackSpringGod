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

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "recommended_movie_id")
    private Movie recommendedMovie;

    @OneToMany(mappedBy = "group")
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<WatchList> watchLists = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<ViewList> viewLists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();


    public Group(Long id, String name, User owner, Movie recommendedMovie, List<MovieUserGroup> movieUserGroups, List<WatchList> watchLists, List<ViewList> viewLists, List<User> members) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.recommendedMovie = recommendedMovie;
        this.movieUserGroups = movieUserGroups;
        this.watchLists = watchLists;
        this.viewLists = viewLists;
        this.members = members;
    }

    public Group() {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Movie getRecommendedMovie() {
        return recommendedMovie;
    }

    public void setRecommendedMovie(Movie recommendedMovie) {
        this.recommendedMovie = recommendedMovie;
    }

    public List<MovieUserGroup> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroup> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}

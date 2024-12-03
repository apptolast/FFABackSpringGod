package com.ffa.back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "watch_lists")
public class WatchList {

    @EmbeddedId
    private WatchListId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // Constructores
    protected WatchList() {}

    public WatchList(WatchListId id, Group group, Movie movie) {
        this.id = id;
        this.group = group;
        this.movie = movie;
    }

    public WatchListId getId() {
        return id;
    }

    public void setId(WatchListId id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

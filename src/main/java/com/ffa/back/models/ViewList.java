package com.ffa.back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "view_lists")
public class ViewList {


    @EmbeddedId
    private ViewListId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // Constructores
    protected ViewList() {}

    public ViewList(ViewListId id, Group group, Movie movie) {
        this.id = id;
        this.group = group;
        this.movie = movie;
    }

    public ViewListId getId() {
        return id;
    }

    public void setId(ViewListId id) {
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

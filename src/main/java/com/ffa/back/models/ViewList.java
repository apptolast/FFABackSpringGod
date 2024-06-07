package com.ffa.back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "view_lists")
public class ViewList {

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    protected ViewList() {}

    public ViewList(Group group, Movie movie) {
        this.group = group;
        this.movie = movie;
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

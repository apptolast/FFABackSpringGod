package com.ffa.back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_user_group")
public class MovieUserGroup {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_movie")
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @Column(nullable = true, name = "to_watch")
    private Boolean toWatch;

    protected MovieUserGroup() {}

    public MovieUserGroup(Movie movie, User user, Group group, Boolean toWatch) {
        this.movie = movie;
        this.user = user;
        this.group = group;
        this.toWatch = toWatch;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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

    public Boolean getToWatch() {
        return toWatch;
    }

    public void setToWatch(Boolean toWatch) {
        this.toWatch = toWatch;
    }
}

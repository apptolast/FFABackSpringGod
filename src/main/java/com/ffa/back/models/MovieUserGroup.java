package com.ffa.back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_user_group")
public class MovieUserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "id_movie")
    private Movie movie;


    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @Column(nullable = true, name = "to_watch")
    private Boolean toWatch;

    public MovieUserGroup() {
    }

    public MovieUserGroup(Movie movie, User user, Group group, Boolean toWatch) {
        this.movie = movie;
        this.user = user;
        this.group = group;
        this.toWatch = toWatch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

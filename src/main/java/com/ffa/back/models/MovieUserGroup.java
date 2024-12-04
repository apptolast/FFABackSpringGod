package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_user_group")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class MovieUserGroup {

    @EmbeddedId
    private MovieUserGroupId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "id_movie")
    private Movie movie;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "id_group")
    private Group group;

    @Column(nullable = true, name = "to_watch")
    private Boolean toWatch;

    // Constructores
    protected MovieUserGroup() {}

    public MovieUserGroup(MovieUserGroupId id, Movie movie, User user, Group group, Boolean toWatch) {
        this.id = id;
        this.movie = movie;
        this.user = user;
        this.group = group;
        this.toWatch = toWatch;
    }

    public MovieUserGroupId getId() {
        return id;
    }

    public void setId(MovieUserGroupId id) {
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

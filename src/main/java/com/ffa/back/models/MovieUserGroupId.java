package com.ffa.back.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieUserGroupId implements Serializable {

    @Column(name = "id_movie")
    private Long movieId;

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_group")
    private Long groupId;

    // Constructores
    public MovieUserGroupId() {
    }

    public MovieUserGroupId(Long movieId, Long userId, Long groupId) {
        this.movieId = movieId;
        this.userId = userId;
        this.groupId = groupId;
    }

    // Getters y Setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    // Equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieUserGroupId that = (MovieUserGroupId) o;

        if (!Objects.equals(movieId, that.movieId)) return false;
        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        int result = movieId != null ? movieId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
package com.ffa.back.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ViewListId implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "movie_id")
    private Long movieId;

    // Constructores
    public ViewListId() {
    }

    public ViewListId(Long groupId, Long movieId) {
        this.groupId = groupId;
        this.movieId = movieId;
    }

    // Getters y Setters
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    // Equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewListId that = (ViewListId) o;

        if (!Objects.equals(groupId, that.groupId)) return false;
        return Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (movieId != null ? movieId.hashCode() : 0);
        return result;
    }
}
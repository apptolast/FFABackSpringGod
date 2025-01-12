package com.ffa.back.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieUserGroupId implements Serializable {
    // Getters y setters (necesarios para JPA)
    private Long group;
    private Long movie;
    private Long user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieUserGroupId)) return false;
        MovieUserGroupId that = (MovieUserGroupId) o;
        return Objects.equals(group, that.group) &&
                Objects.equals(movie, that.movie) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, movie, user);
    }
}
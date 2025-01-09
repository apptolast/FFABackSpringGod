package com.ffa.back.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewListId implements Serializable {
    private Long group;
    private Long movie;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ViewListId)) return false;
        ViewListId that = (ViewListId) o;
        return Objects.equals(group, that.group) &&
                Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, movie);
    }
}
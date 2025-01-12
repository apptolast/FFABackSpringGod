package com.ffa.back.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "movie_user_group",
        indexes = {
                @Index(name = "idx_movie_user_group_movie", columnList = "movie_id"),
                @Index(name = "idx_movie_user_group_user", columnList = "user_id"),
                @Index(name = "idx_movie_user_group_group", columnList = "group_id")
        })
@IdClass(MovieUserGroupId.class)
@NoArgsConstructor
public class MovieUserGroup {
    // Getters y setters
    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "to_watch")
    private Boolean toWatch;


}
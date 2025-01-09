package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.ConstructorParameters;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "movies")
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column
    private String language;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cached_in_redis")
    private Boolean cachedInRedis = false;

    @Column(name = "last_cached_at")
    private LocalDateTime lastCachedAt;

    @Column(name = "content_type", length = 10)
    private String contentType = "MOVIE";

    @Column(name = "redis_key", insertable = false, updatable = false)
    private String redisKey;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<WatchList> watchLists = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<ViewList> viewLists = new ArrayList<>();

    @OneToOne(mappedBy = "movie")
    @JsonManagedReference
    private MovieMetrics metrics;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<ContentStatus> contentStatuses = new ArrayList<>();

    @ManyToMany(mappedBy = "watchlistMovies", fetch = FetchType.LAZY)
    private List<User> watchlistUsers = new ArrayList<>();

    public void addMovieUserGroup(MovieUserGroup movieUserGroup) {
        movieUserGroups.add(movieUserGroup);
        movieUserGroup.setMovie(this);
    }

    public void removeMovieUserGroup(MovieUserGroup movieUserGroup) {
        movieUserGroups.remove(movieUserGroup);
        movieUserGroup.setMovie(null);
    }
}

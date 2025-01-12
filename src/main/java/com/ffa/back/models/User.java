package com.ffa.back.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "firebase_uuid")
    private String firebaseUuid;

    @Column(nullable = false)
    private String provider;

    @Column
    private String role;

    @Column(name = "auth_time")
    private Long authTime;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "expiry_time")
    private Long expiryTime;

    @Column(name = "issue_time")
    private Long issueTime;

    @Column(name = "identity_provider")
    private String identityProvider;

    @Column(nullable = false)
    private String sub;

    @ManyToOne
    @JoinColumn(name = "id_language")
    private Language language;

    @OneToMany(mappedBy = "owner")
    private List<Group> ownedGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_viewed_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> viewedMovies = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_to_watch_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> toWatchMovies = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ContentStatus> contentStatuses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_watchlist_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @JsonManagedReference
    private List<Movie> watchlistMovies = new ArrayList<>();

}
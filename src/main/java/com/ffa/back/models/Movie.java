package com.ffa.back.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(nullable = true)
    private List<Integer> genre_ids;

    @Column(nullable = true)
    private String language;

    @Column(nullable = true)
    private String synopsis;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    private Boolean adult;

    @Column(nullable = true)
    private Date release_date;

    @Column(nullable = true)
    private Double vote_average;

    @Column(nullable = true)
    private Integer vote_count;

    @OneToMany(mappedBy = "movie")
    private List<WatchList> watchLists = new ArrayList<>();


    @OneToMany(mappedBy = "movie")
    private List<ViewList> viewLists = new ArrayList<>();


    @OneToMany(mappedBy = "movie")
    private List<MovieUserGroup> movieUserGroups = new ArrayList<>();


    public Movie() {
    }

    public Movie(Long id, String title, List<Genre> genres, Long tmdbId, List<Integer> genre_ids, String language, String synopsis, String image, Boolean adult, Date release_date, Double vote_average, Integer vote_count, List<WatchList> watchLists, List<ViewList> viewLists, List<MovieUserGroup> movieUserGroups) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.tmdbId = tmdbId;
        this.genre_ids = genre_ids;
        this.language = language;
        this.synopsis = synopsis;
        this.image = image;
        this.adult = adult;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.watchLists = watchLists;
        this.viewLists = viewLists;
        this.movieUserGroups = movieUserGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public List<WatchList> getWatchLists() {
        return watchLists;
    }

    public void setWatchLists(List<WatchList> watchLists) {
        this.watchLists = watchLists;
    }

    public List<ViewList> getViewLists() {
        return viewLists;
    }

    public void setViewLists(List<ViewList> viewLists) {
        this.viewLists = viewLists;
    }

    public List<MovieUserGroup> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroup> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
    }
}

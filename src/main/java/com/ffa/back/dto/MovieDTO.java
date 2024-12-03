package com.ffa.back.dto;

import java.util.Date;
import java.util.List;

public class MovieDTO {
    private Long id;
    private String title;
    private String language;
    private String synopsis;
    private String image;
    private Boolean adult;
    private Date release_date;
    private Double vote_average;
    private Integer vote_count;
    private List<GenreDTO> genres;
    private List<UserDTO> usersViewed;
    private List<UserDTO> usersWatchlist;
    private List<WatchListDTO> watchLists;
    private List<ViewListDTO> viewLists;
    private List<MovieUserGroupDTO> movieUserGroups;

    // Constructores
    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String language, String synopsis, String image, Boolean adult,
                    Date release_date, Double vote_average, Integer vote_count, List<GenreDTO> genres,
                    List<UserDTO> usersViewed, List<UserDTO> usersWatchlist, List<WatchListDTO> watchLists,
                    List<ViewListDTO> viewLists, List<MovieUserGroupDTO> movieUserGroups) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.synopsis = synopsis;
        this.image = image;
        this.adult = adult;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.genres = genres;
        this.usersViewed = usersViewed;
        this.usersWatchlist = usersWatchlist;
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

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    public List<UserDTO> getUsersViewed() {
        return usersViewed;
    }

    public void setUsersViewed(List<UserDTO> usersViewed) {
        this.usersViewed = usersViewed;
    }

    public List<UserDTO> getUsersWatchlist() {
        return usersWatchlist;
    }

    public void setUsersWatchlist(List<UserDTO> usersWatchlist) {
        this.usersWatchlist = usersWatchlist;
    }

    public List<WatchListDTO> getWatchLists() {
        return watchLists;
    }

    public void setWatchLists(List<WatchListDTO> watchLists) {
        this.watchLists = watchLists;
    }

    public List<ViewListDTO> getViewLists() {
        return viewLists;
    }

    public void setViewLists(List<ViewListDTO> viewLists) {
        this.viewLists = viewLists;
    }

    public List<MovieUserGroupDTO> getMovieUserGroups() {
        return movieUserGroups;
    }

    public void setMovieUserGroups(List<MovieUserGroupDTO> movieUserGroups) {
        this.movieUserGroups = movieUserGroups;
    }
}

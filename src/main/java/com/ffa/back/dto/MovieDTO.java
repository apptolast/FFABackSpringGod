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
    private List<Long> genreIds;
    private List<Long> userViewedIds;
    private List<Long> userWatchlistIds;
    private List<Long> watchListIds;
    private List<Long> viewListIds;
    private List<Long> movieUserGroupIds;

    // Constructores
    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String language, String synopsis, String image, Boolean adult,
                    Date release_date, Double vote_average, Integer vote_count, List<Long> genreIds,
                    List<Long> userViewedIds, List<Long> userWatchlistIds, List<Long> watchListIds,
                    List<Long> viewListIds, List<Long> movieUserGroupIds) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.synopsis = synopsis;
        this.image = image;
        this.adult = adult;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.genreIds = genreIds;
        this.userViewedIds = userViewedIds;
        this.userWatchlistIds = userWatchlistIds;
        this.watchListIds = watchListIds;
        this.viewListIds = viewListIds;
        this.movieUserGroupIds = movieUserGroupIds;
    }

    // Getters y Setters
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

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Long> getUserViewedIds() {
        return userViewedIds;
    }

    public void setUserViewedIds(List<Long> userViewedIds) {
        this.userViewedIds = userViewedIds;
    }

    public List<Long> getUserWatchlistIds() {
        return userWatchlistIds;
    }

    public void setUserWatchlistIds(List<Long> userWatchlistIds) {
        this.userWatchlistIds = userWatchlistIds;
    }

    public List<Long> getWatchListIds() {
        return watchListIds;
    }

    public void setWatchListIds(List<Long> watchListIds) {
        this.watchListIds = watchListIds;
    }

    public List<Long> getViewListIds() {
        return viewListIds;
    }

    public void setViewListIds(List<Long> viewListIds) {
        this.viewListIds = viewListIds;
    }

    public List<Long> getMovieUserGroupIds() {
        return movieUserGroupIds;
    }

    public void setMovieUserGroupIds(List<Long> movieUserGroupIds) {
        this.movieUserGroupIds = movieUserGroupIds;
    }
}

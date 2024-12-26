package com.ffa.back.dto;

import java.util.Date;
import java.util.List;

public class MovieReponseIDdto {

    private Long id;
    private Long tmdbId;
    private String title;
    private String language;
    private String synopsis;
    private String image;
    private Boolean adult;
    private Date releaseDate;
    private Double voteAverage;
    private Integer voteCount;
    private List<Integer> genreIds;

    public MovieReponseIDdto(Long id, Long tmdbId, String title, String language, String synopsis, String image, Boolean adult, Date releaseDate, Double voteAverage, Integer voteCount, List<Integer> genreIds) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.title = title;
        this.language = language;
        this.synopsis = synopsis;
        this.image = image;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.genreIds = genreIds;
    }

    public MovieReponseIDdto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}

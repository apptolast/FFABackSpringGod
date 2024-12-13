package com.ffa.back.dto;

import java.util.List;

public class MovieUsersDTO {

    private List<Long> usersId;
    private Long movieId;

    public MovieUsersDTO(List<Long> usersId, Long movieId) {
        this.usersId = usersId;
        this.movieId = movieId;
    }

    public MovieUsersDTO() {
    }

    // Getters y setters
    public List<Long> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<Long> usersId) {
        this.usersId = usersId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }


}
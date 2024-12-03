package com.ffa.back.dto;

public class WatchListDTO {
    private WatchListIdDTO id;
    private GroupDTO group;
    private MovieDTO movie;

    // Constructores
    public WatchListDTO() {
    }

    public WatchListDTO(WatchListIdDTO id, GroupDTO group, MovieDTO movie) {
        this.id = id;
        this.group = group;
        this.movie = movie;
    }


    public WatchListIdDTO getId() {
        return id;
    }

    public void setId(WatchListIdDTO id) {
        this.id = id;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }
}

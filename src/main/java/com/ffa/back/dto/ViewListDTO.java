package com.ffa.back.dto;

public class ViewListDTO {
    private ViewListIdDTO id;
    private GroupDTO group;
    private MovieDTO movie;

    // Constructores
    public ViewListDTO() {
    }

    public ViewListDTO(ViewListIdDTO id, GroupDTO group, MovieDTO movie) {
        this.id = id;
        this.group = group;
        this.movie = movie;
    }

    public ViewListIdDTO getId() {
        return id;
    }

    public void setId(ViewListIdDTO id) {
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

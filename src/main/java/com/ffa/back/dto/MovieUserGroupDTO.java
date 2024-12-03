package com.ffa.back.dto;

public class MovieUserGroupDTO {
    private MovieUserGroupIdDTO id;
    private MovieDTO movie;
    private UserDTO user;
    private GroupDTO group;
    private Boolean toWatch;

    // Constructores
    public MovieUserGroupDTO() {
    }

    public MovieUserGroupDTO(MovieUserGroupIdDTO id, MovieDTO movie, UserDTO user,
                             GroupDTO group, Boolean toWatch) {
        this.id = id;
        this.movie = movie;
        this.user = user;
        this.group = group;
        this.toWatch = toWatch;
    }

    public MovieUserGroupIdDTO getId() {
        return id;
    }

    public void setId(MovieUserGroupIdDTO id) {
        this.id = id;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public Boolean getToWatch() {
        return toWatch;
    }

    public void setToWatch(Boolean toWatch) {
        this.toWatch = toWatch;
    }
}

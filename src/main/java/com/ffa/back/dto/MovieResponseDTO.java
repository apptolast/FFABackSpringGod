package com.ffa.back.dto;

public class MovieResponseDTO {
    private Long id;
    private String title;

    public MovieResponseDTO() {
    }

    public MovieResponseDTO(String title, Long id) {
        this.title = title;
        this.id = id;
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
}

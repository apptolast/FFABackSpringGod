package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String email;
    private String language;
    private List<MovieResponseDTO> watchedMovies;
    private List<MovieResponseDTO> toWatchMovies;
    private List<Long> joinedGroupIds;
}
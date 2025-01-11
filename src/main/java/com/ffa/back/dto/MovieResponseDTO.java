package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDTO {
    private Long movieId;
    private String title;
    private List<Long> groupIds;

    public MovieResponseDTO(List<Long> groupIds, Long movieId) {
        this.groupIds = groupIds;
        this.movieId = movieId;
    }
}

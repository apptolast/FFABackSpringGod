package com.ffa.back.mapper;

import com.ffa.back.dto.MovieResponseDTO;
import com.ffa.back.models.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    public MovieResponseDTO toDto(Movie movie) {
        if (movie == null) return null;

        // Lógica para mapear
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setMovieId(movie.getId());
        dto.setTitle(movie.getTitle());
        // Podrías setear groupIds a empty o algo derivado de movie.getMovieUserGroups()
        dto.setGroupIds(List.of());
        return dto;
    }
}

package com.ffa.back.mappers;

import com.ffa.back.dto.GenreDTO;
import com.ffa.back.models.Genre;
import com.ffa.back.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "movieIds", expression = "java(mapMoviesToIds(genre.getMovies()))")
    GenreDTO toGenreDTO(Genre genre);

    @Mapping(target = "movies", ignore = true)
    Genre toGenre(GenreDTO genreDTO);

    // Métodos auxiliares
    default List<Long> mapMoviesToIds(List<Movie> movies) {
        if (movies == null) {
            return null;
        }
        return movies.stream().map(Movie::getId).collect(Collectors.toList());
    }
}
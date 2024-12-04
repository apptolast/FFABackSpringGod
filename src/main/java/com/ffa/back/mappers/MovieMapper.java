package com.ffa.back.mappers;

import com.ffa.back.dto.MovieDTO;
import com.ffa.back.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface MovieMapper {

    @Mapping(target = "genres", source = "genres")
    @Mapping(target = "usersViewed", ignore = true)     // Ignorar para evitar ciclos
    @Mapping(target = "usersWatchlist", ignore = true)  // Ignorar para evitar ciclos
    @Mapping(target = "watchLists", ignore = true)      // Ignorar para evitar ciclos
    @Mapping(target = "viewLists", ignore = true)       // Ignorar para evitar ciclos
    @Mapping(target = "movieUserGroups", ignore = true)
        // Ignorar para evitar ciclos
    MovieDTO toMovieDTO(Movie movie);

    // Métodos inversos si es necesario
    // Movie toMovie(MovieDTO movieDTO);
}
package com.ffa.back.mappers;

import com.ffa.back.dto.GenreDTO;
import com.ffa.back.models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "movies", ignore = true)
        // Ignorar para evitar ciclos
    GenreDTO toGenreDTO(Genre genre);

    // Métodos inversos si es necesario
    // Genre toGenre(GenreDTO genreDTO);
}
package com.ffa.back.mappers;


import com.ffa.back.dto.MovieUserGroupDTO;
import com.ffa.back.models.MovieUserGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MovieUserGroupIdMapper.class})
public interface MovieUserGroupMapper {

    // Mapeo de Entidad a DTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "toWatch", source = "toWatch")
    MovieUserGroupDTO toMovieUserGroupDTO(MovieUserGroup movieUserGroup);

    // Mapeo de DTO a Entidad
    @Mapping(target = "id", source = "id")
    @Mapping(target = "toWatch", source = "toWatch")
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "group", ignore = true)
    MovieUserGroup toMovieUserGroup(MovieUserGroupDTO movieUserGroupDTO);
}
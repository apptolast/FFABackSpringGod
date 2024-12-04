package com.ffa.back.mappers;


import com.ffa.back.dto.MovieUserGroupDTO;
import com.ffa.back.models.MovieUserGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MovieUserGroupIdMapper.class})
public interface MovieUserGroupMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "movieId", expression = "java(movieUserGroup.getMovie() != null ? movieUserGroup.getMovie().getId() : null)")
    @Mapping(target = "userId", expression = "java(movieUserGroup.getUser() != null ? movieUserGroup.getUser().getId() : null)")
    @Mapping(target = "groupId", expression = "java(movieUserGroup.getGroup() != null ? movieUserGroup.getGroup().getId() : null)")
    @Mapping(target = "toWatch", source = "toWatch")
    MovieUserGroupDTO toMovieUserGroupDTO(MovieUserGroup movieUserGroup);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "toWatch", source = "toWatch")
    MovieUserGroup toMovieUserGroup(MovieUserGroupDTO movieUserGroupDTO);
}
package com.ffa.back.mappers;


import com.ffa.back.dto.MovieUserGroupDTO;
import com.ffa.back.dto.MovieUserGroupIdDTO;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.MovieUserGroupId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MovieMapper.class, UserMapper.class, GroupMapper.class})
public interface MovieUserGroupMapper {

    // Mapear MovieUserGroup a MovieUserGroupDTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "movie", target = "movie")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "toWatch", target = "toWatch")
    MovieUserGroupDTO toMovieUserGroupDTO(MovieUserGroup movieUserGroup);

    // Mapear MovieUserGroupDTO a MovieUserGroup
    @Mapping(source = "id", target = "id")
    @Mapping(source = "movie", target = "movie")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "toWatch", target = "toWatch")
    MovieUserGroup toMovieUserGroup(MovieUserGroupDTO movieUserGroupDTO);

    // Mapear MovieUserGroupId a MovieUserGroupIdDTO
    MovieUserGroupIdDTO toMovieUserGroupIdDTO(MovieUserGroupId id);

    // Mapear MovieUserGroupIdDTO a MovieUserGroupId
    MovieUserGroupId toMovieUserGroupId(MovieUserGroupIdDTO idDTO);
}
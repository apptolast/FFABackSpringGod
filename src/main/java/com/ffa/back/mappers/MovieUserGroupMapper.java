package com.ffa.back.mappers;


import com.ffa.back.dto.MovieUserGroupDTO;
import com.ffa.back.dto.MovieUserGroupIdDTO;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.MovieUserGroupId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {MovieMapper.class, UserMapper.class, GroupMapper.class})
public interface MovieUserGroupMapper {

    MovieUserGroupMapper INSTANCE = Mappers.getMapper(MovieUserGroupMapper.class);

    @Mapping(source = "id.movieId", target = "movieId")
    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "id.groupId", target = "groupId")
    MovieUserGroupDTO toMovieUserGroupDTO(MovieUserGroup movieUserGroup);

    @Mapping(source = "movieId", target = "id.movieId")
    @Mapping(source = "userId", target = "id.userId")
    @Mapping(source = "groupId", target = "id.groupId")
    MovieUserGroup toMovieUserGroup(MovieUserGroupDTO movieUserGroupDTO);

    @Mapping(source = "movieId", target = "movieId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "groupId", target = "groupId")
    MovieUserGroupIdDTO toMovieUserGroupIdDTO(MovieUserGroupId id);

    @Mapping(source = "movieId", target = "movieId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "groupId", target = "groupId")
    MovieUserGroupId toMovieUserGroupId(MovieUserGroupIdDTO idDTO);
}
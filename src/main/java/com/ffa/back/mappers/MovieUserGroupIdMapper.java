package com.ffa.back.mappers;


import com.ffa.back.dto.MovieUserGroupIdDTO;
import com.ffa.back.models.MovieUserGroupId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieUserGroupIdMapper {
    MovieUserGroupIdDTO toMovieUserGroupIdDTO(MovieUserGroupId id);

    MovieUserGroupId toMovieUserGroupId(MovieUserGroupIdDTO idDTO);
}
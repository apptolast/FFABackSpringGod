package com.ffa.back.mappers;

import com.ffa.back.dto.UserDTO;
import com.ffa.back.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LanguageMapper.class, GroupMapper.class, MovieMapper.class})
public interface UserMapper {

    @Mapping(target = "language", source = "language")
    @Mapping(target = "ownedGroups", source = "ownedGroups")
    @Mapping(target = "groups", source = "groups")
    @Mapping(target = "viewedMovies", source = "viewedMovies")
    @Mapping(target = "watchlistMovies", source = "watchlistMovies")
    UserDTO toUserDTO(User user);

    // Métodos inversos si es necesario
    // User toUser(UserDTO userDTO);
}
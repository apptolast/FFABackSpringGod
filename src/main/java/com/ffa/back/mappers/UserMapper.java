package com.ffa.back.mappers;

import com.ffa.back.dto.UserDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "languageId", expression = "java(user.getLanguage() != null ? user.getLanguage().getId() : null)")
    @Mapping(target = "ownedGroupIds", expression = "java(mapOwnedGroupsToIds(user.getOwnedGroups()))")
    @Mapping(target = "groupIds", expression = "java(mapGroupsToIds(user.getGroups()))")
    @Mapping(target = "viewedMovieIds", expression = "java(mapViewedMoviesToIds(user.getViewedMovies()))")
    @Mapping(target = "watchlistMovieIds", expression = "java(mapWatchlistMoviesToIds(user.getWatchlistMovies()))")
    UserDTO toUserDTO(User user);

    @Mapping(target = "language", ignore = true)
    @Mapping(target = "ownedGroups", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "viewedMovies", ignore = true)
    @Mapping(target = "watchlistMovies", ignore = true)
    User toUser(UserDTO userDTO);

    // Métodos auxiliares
    default List<Long> mapOwnedGroupsToIds(List<Group> ownedGroups) {
        if (ownedGroups == null) {
            return null;
        }
        return ownedGroups.stream().map(Group::getId).collect(Collectors.toList());
    }

    default List<Long> mapGroupsToIds(List<Group> groups) {
        if (groups == null) {
            return null;
        }
        return groups.stream().map(Group::getId).collect(Collectors.toList());
    }

    default List<Long> mapViewedMoviesToIds(List<Movie> viewedMovies) {
        if (viewedMovies == null) {
            return null;
        }
        return viewedMovies.stream().map(Movie::getId).collect(Collectors.toList());
    }

    default List<Long> mapWatchlistMoviesToIds(List<Movie> watchlistMovies) {
        if (watchlistMovies == null) {
            return null;
        }
        return watchlistMovies.stream().map(Movie::getId).collect(Collectors.toList());
    }
}
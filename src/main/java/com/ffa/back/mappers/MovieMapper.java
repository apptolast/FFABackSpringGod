package com.ffa.back.mappers;

import com.ffa.back.dto.MovieDTO;
import com.ffa.back.models.Genre;
import com.ffa.back.models.Movie;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.MovieUserGroupId;
import com.ffa.back.models.User;
import com.ffa.back.models.WatchList;
import com.ffa.back.models.ViewList;
import com.ffa.back.models.ViewListId;
import com.ffa.back.models.WatchListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "genreIds", expression = "java(mapGenresToIds(movie.getGenres()))")
    @Mapping(target = "userViewedIds", expression = "java(mapUsersToIds(movie.getUsersViewed()))")
    @Mapping(target = "userWatchlistIds", expression = "java(mapUsersToIds(movie.getUsersWatchlist()))")
    @Mapping(target = "watchListIds", expression = "java(mapWatchListsToIds(movie.getWatchLists()))")
    @Mapping(target = "viewListIds", expression = "java(mapViewListsToIds(movie.getViewLists()))")
    @Mapping(target = "movieUserGroupIds", expression = "java(mapMovieUserGroupsToIds(movie.getMovieUserGroups()))")
    MovieDTO toMovieDTO(Movie movie);

    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "usersViewed", ignore = true)
    @Mapping(target = "usersWatchlist", ignore = true)
    @Mapping(target = "watchLists", ignore = true)
    @Mapping(target = "viewLists", ignore = true)
    @Mapping(target = "movieUserGroups", ignore = true)
    Movie toMovie(MovieDTO movieDTO);

    // Métodos auxiliares
    default List<Long> mapGenresToIds(List<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream().map(Genre::getId).collect(Collectors.toList());
    }

    default List<Long> mapUsersToIds(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    default List<Long> mapWatchListsToIds(List<WatchList> watchLists) {
        if (watchLists == null) {
            return null;
        }
        return watchLists.stream()
                .map(watchList -> watchList.getId().getMovieId())
                .collect(Collectors.toList());
    }

    default List<Long> mapViewListsToIds(List<ViewList> viewLists) {
        if (viewLists == null) {
            return null;
        }
        return viewLists.stream()
                .map(viewList -> viewList.getId().getMovieId())
                .collect(Collectors.toList());
    }

    default List<Long> mapMovieUserGroupsToIds(List<MovieUserGroup> movieUserGroups) {
        if (movieUserGroups == null) {
            return null;
        }
        return movieUserGroups.stream()
                .map(movieUserGroup -> movieUserGroup.getId().getMovieId())
                .collect(Collectors.toList());
    }
}
package com.ffa.back.mapper;

import com.ffa.back.dto.MovieResponseDTO;
import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Language;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {
    public UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                Optional.ofNullable(user.getLanguage())
                        .map(Language::getLanguage)
                        .orElse(null),
                toMovieResponseDTOList(user.getWatchlistMovies()),
                toMovieResponseDTOList(user.getToWatchMovies()),
                user.getOwnedGroups().stream()
                        .map(Group::getId)
                        .toList()
        );
    }

    private List<MovieResponseDTO> toMovieResponseDTOList(List<Movie> movies) {
        return Optional.ofNullable(movies)
                .orElse(List.of())
                .stream()
                .map(this::toMovieResponseDTO)
                .toList();
    }

    private MovieResponseDTO toMovieResponseDTO(Movie movie) {
        List<Long> groupIds = movie.getMovieUserGroups().stream()
                .map(mug -> mug.getGroup().getId())
                .distinct()
                .toList();
        return new MovieResponseDTO(groupIds, movie.getId());
    }
}
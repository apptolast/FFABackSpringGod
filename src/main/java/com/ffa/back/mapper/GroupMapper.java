package com.ffa.back.mapper;

import com.ffa.back.dto.GroupResponseDTO;
import com.ffa.back.dto.MovieUsersDTO;
import com.ffa.back.models.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final UserMapper userMapper;
    private final MovieMapper movieMapper;

    public GroupResponseDTO toDto(Group group) {
        return new GroupResponseDTO(
                group.getId(),
                group.getOwner().getId(),
                group.getName(),
                group.getMembers().stream()
                        .map(userMapper::toDto)
                        .toList(),
                getWatchedMovies(group),
                getToWatchMovies(group),
                group.getRecommendedMovie() != null ?
                        movieMapper.toDto(group.getRecommendedMovie()) : null
        );
    }

    private List<MovieUsersDTO> getWatchedMovies(Group group) {
        return group.getMovieUserGroups().stream()
                .filter(mug -> !mug.getToWatch())
                .collect(groupingBy(
                        mug -> mug.getMovie().getId(),
                        mapping(mug -> mug.getUser().getId(), toList())
                ))
                .entrySet().stream()
                .map(entry -> new MovieUsersDTO(entry.getValue(), entry.getKey()))
                .toList();
    }

    // Implementar getToWatchMovies...
}

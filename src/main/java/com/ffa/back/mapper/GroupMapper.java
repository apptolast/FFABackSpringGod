package com.ffa.back.mapper;

import com.ffa.back.dto.GroupResponseDTO;
import com.ffa.back.dto.MovieUsersDTO;
import com.ffa.back.models.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final UserMapper userMapper;
    private final MovieMapper movieMapper; // <-- Inyección para "movieMapper"

    public GroupResponseDTO toDto(Group group) {
        return new GroupResponseDTO(
                group.getId(),
                group.getOwner() != null ? group.getOwner().getId() : null,
                group.getName(),
                group.getMembers().stream()
                        .map(userMapper::toDto)
                        .toList(),
                getWatchedMovies(group),
                getToWatchMovies(group),
                group.getRecommendedMovie() != null
                        ? movieMapper.toDto(group.getRecommendedMovie())
                        : null
        );
    }

    // Filtra donde toWatch = false
    private List<MovieUsersDTO> getWatchedMovies(Group group) {
        return group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.FALSE.equals(mug.getToWatch()))
                .collect(Collectors.groupingBy(
                        mug -> mug.getMovie().getId(),
                        Collectors.mapping(mug -> mug.getUser().getId(), Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> new MovieUsersDTO(entry.getValue(), entry.getKey()))
                .toList();
    }

    // Filtra donde toWatch = true
    private List<MovieUsersDTO> getToWatchMovies(Group group) {
        return group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.TRUE.equals(mug.getToWatch()))
                .collect(Collectors.groupingBy(
                        mug -> mug.getMovie().getId(),
                        Collectors.mapping(mug -> mug.getUser().getId(), Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> new MovieUsersDTO(entry.getValue(), entry.getKey()))
                .toList();
    }
}

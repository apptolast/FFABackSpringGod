package com.ffa.back.services;

import com.ffa.back.dto.MovieGroupStatusDTO;
import com.ffa.back.enums.MovieGroupStatus;
import com.ffa.back.models.ContentStatus;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import com.ffa.back.repositories.ContentStatusRepository;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentStatusService {

    private final ContentStatusRepository contentStatusRepository;
    private final MovieRepository movieRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /**
     * Marca la película (movieId) para un usuario (currentUser) en un grupo (groupId)
     * con el status dado ("TO_WATCH", "WATCHED", etc.).
     */
    public void markMovieStatus(Long movieId, Long groupId, User currentUser, String status) {
        // 1) Buscar o crear la Movie
        Movie movie = findOrCreateMovie(movieId, "MOVIE");  // O "SERIE" si procede

        // 2) Cargar el Group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // 3) Mirar si ya existe un ContentStatus
        Optional<ContentStatus> existing = contentStatusRepository.findByMovieAndUserAndGroup(movie, currentUser, group);
        if (existing.isPresent()) {
            ContentStatus cs = existing.get();
            cs.setStatus(status);
            cs.setUpdatedAt(LocalDateTime.now());
            contentStatusRepository.save(cs);
        } else {
            ContentStatus cs = new ContentStatus();
            cs.setMovie(movie);
            cs.setUser(currentUser);
            cs.setGroup(group);
            cs.setStatus(status);
            cs.setCreatedAt(LocalDateTime.now());
            cs.setUpdatedAt(LocalDateTime.now());
            contentStatusRepository.save(cs);
        }
    }

    private Movie findOrCreateMovie(Long tmdbId, String contentType) {
        return movieRepository.findByTmdbId(tmdbId)
                .orElseGet(() -> {
                    Movie m = new Movie();
                    m.setTmdbId(tmdbId);
                    m.setTitle("Unknown Title");
                    m.setContentType(contentType);
                    return movieRepository.save(m);
                });
    }


    /**
     * Elimina la fila en content_status (o podrías poner status="REMOVED")
     */
    public void removeMovieStatus(Long movieId, Long groupId, User currentUser) {
        Movie movie = findOrCreateMovie(movieId, "MOVIE");
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Optional<ContentStatus> existing = contentStatusRepository.findByMovieAndUserAndGroup(movie, currentUser, group);
        existing.ifPresent(contentStatusRepository::delete);
    }

    /**
     * Construye un DTO con la info del estado actual
     */
    public MovieGroupStatus getMovieGroupStatus(Long groupId, Long movieId, Long userId) {
        List<ContentStatus> allStatuses = contentStatusRepository
                .findAllByMovieIdAndGroupId(movieId, groupId);

        if (allStatuses.isEmpty()) {
            return MovieGroupStatus.NOT_IN_GROUP;
        }
        // 1) usuario actual
        Optional<ContentStatus> userStatusOpt = allStatuses.stream()
                .filter(cs -> cs.getUser().getId().equals(userId))
                .findFirst();
        if (userStatusOpt.isPresent()) {
            String status = userStatusOpt.get().getStatus().toUpperCase();
            switch (status) {
                case "TO_WATCH":
                    return MovieGroupStatus.TO_WATCH_BY_USER;
                case "WATCHED":
                    return MovieGroupStatus.WATCHED_BY_USER;
            }
        }
        // 2) otros usuarios
        boolean anyToWatch = allStatuses.stream()
                .anyMatch(cs -> "TO_WATCH".equalsIgnoreCase(cs.getStatus()));
        boolean anyWatched = allStatuses.stream()
                .anyMatch(cs -> "WATCHED".equalsIgnoreCase(cs.getStatus()));

        if (anyToWatch) return MovieGroupStatus.TO_WATCH_BY_OTHER;
        if (anyWatched) return MovieGroupStatus.WATCHED_BY_OTHER;
        return MovieGroupStatus.NOT_IN_GROUP;
    }
}
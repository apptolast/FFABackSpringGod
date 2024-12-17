package com.ffa.back.services;

import com.ffa.back.dto.MovieResponseDTO;
import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
import com.ffa.back.models.*;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.UserRepository;
import jakarta.persistence.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieRecommendationService {

    @Autowired
    private MovieRepository movieRepository;

    @Cacheable(value = "movieRecommendations", key = "#group.id")
    public Movie recommendMovie(Group group) {
        // Obtener géneros más vistos en el grupo
        Map<Genre, Long> genreCount = group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.FALSE.equals(mug.getToWatch()))
                .flatMap(mug -> mug.getMovie().getGenres().stream())
                .collect(Collectors.groupingBy(
                        genre -> genre,
                        Collectors.counting()
                ));

        // Obtener IDs de películas ya vistas o en la lista de ver
        Set<Long> existingMovieIds = group.getMovieUserGroups().stream()
                .map(mug -> mug.getMovie().getId())
                .collect(Collectors.toSet());

        // Si no hay géneros vistos, retornar null
        if (genreCount.isEmpty()) {
            return null;
        }

        // Encontrar película similar basada en géneros
        return movieRepository.findTopByGenresInAndIdNotIn(
                genreCount.keySet(),
                existingMovieIds
        ).orElse(null);
    }

}
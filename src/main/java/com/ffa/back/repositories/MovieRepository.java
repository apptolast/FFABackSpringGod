package com.ffa.back.repositories;

import com.ffa.back.models.Genre;
import com.ffa.back.models.Movie;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT m FROM Movie m JOIN m.genres g WHERE g IN :genres AND m.id NOT IN :excludedIds ORDER BY RAND() LIMIT 1")
    Optional<Movie> findTopByGenresInAndIdNotIn(Set<Genre> genres, Set<Long> excludedIds);

    Optional<Movie> findByTmdbId(Long tmdbId);

    @Query("SELECT m FROM Movie m WHERE m.id IN :movieIds")
    List<Movie> findAllByIds(@Param("movieIds") List<Long> movieIds);

}

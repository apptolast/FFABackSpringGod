package com.ffa.back.repositories;

import com.ffa.back.models.ContentStatus;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContentStatusRepository extends JpaRepository<ContentStatus, Long> {

    /**
     * Encuentra un registro (content_status) exacto por la combinación
     * de movie, user y group.
     */
    Optional<ContentStatus> findByMovieAndUserAndGroup(Movie movie, User user, Group group);

    /**
     * Retorna todas las filas para un groupId y movieId dados.
     */
    @Query("SELECT cs FROM ContentStatus cs " +
            "WHERE cs.movie.id = :movieId AND cs.group.id = :groupId")
    List<ContentStatus> findAllByMovieIdAndGroupId(Long movieId, Long groupId);


}

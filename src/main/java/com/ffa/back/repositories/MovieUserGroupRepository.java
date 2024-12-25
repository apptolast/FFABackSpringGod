package com.ffa.back.repositories;

import com.ffa.back.models.Group;
import com.ffa.back.models.MovieUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieUserGroupRepository extends JpaRepository<MovieUserGroup, Long> {
    List<MovieUserGroup> findByGroup(Group group);

    @Query("SELECT mug FROM MovieUserGroup mug WHERE mug.movie.id = :movieId AND mug.group.id IN :groupIds")
    List<MovieUserGroup> findByMovieIdAndGroupIds(Long movieId, List<Long> groupIds);

    // Para encontrar una entrada específica para eliminar
    @Query("SELECT mug FROM MovieUserGroup mug WHERE mug.movie.id = :movieId AND mug.group.id = :groupId AND mug.user.id = :userId")
    Optional<MovieUserGroup> findByMovieIdAndGroupIdAndUserId(Long movieId, Long groupId, Long userId);

    // Para verificar si ya existe la relación
    boolean existsByMovieIdAndGroupIdAndUserId(Long movieId, Long groupId, Long userId);

    // Para eliminar una entrada específica
    @Modifying
    @Query("DELETE FROM MovieUserGroup mug WHERE mug.movie.id = :movieId AND mug.group.id = :groupId AND mug.user.id = :userId")
    void deleteByMovieIdAndGroupIdAndUserId(Long movieId, Long groupId, Long userId);
}
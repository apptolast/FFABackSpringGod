package com.ffa.back.repositories;

import com.ffa.back.models.Group;
import com.ffa.back.models.MovieUserGroup;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieUserGroupRepository extends JpaRepository<MovieUserGroup, Long> {
    List<MovieUserGroup> findByGroup(Group group);

    @Query("SELECT mug FROM MovieUserGroup mug JOIN mug.movie m WHERE m.tmdbId = :movieId AND mug.group.id IN :groupIds")
    List<MovieUserGroup> findByMovieIdAndGroupIds(@Param("movieId") Long movieId, @Param("groupIds") List<Long> groupIds);

    @Query("SELECT mug FROM MovieUserGroup mug JOIN mug.movie m " +
            "WHERE m.tmdbId = :movieId AND mug.group.id = :groupId AND mug.user.id = :userId")
    Optional<MovieUserGroup> findByMovieIdAndGroupIdAndUserId(
            @Param("movieId") Long movieId,
            @Param("groupId") Long groupId,
            @Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MovieUserGroup m " +
            "JOIN m.movie mov WHERE mov.tmdbId = :movieId AND m.group.id = :groupId AND m.user.id = :userId")
    boolean existsByMovieIdAndGroupIdAndUserId(
            @Param("movieId") Long movieId,
            @Param("groupId") Long groupId,
            @Param("userId") Long userId);

    // Para eliminar una entrada específica
    @Modifying
    @Query("DELETE FROM MovieUserGroup mug WHERE mug.movie.id = :movieId AND mug.group.id = :groupId AND mug.user.id = :userId")
    void deleteByMovieIdAndGroupIdAndUserId(Long movieId, Long groupId, Long userId);
}
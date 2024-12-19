package com.ffa.back.repositories;

import com.ffa.back.models.Group;
import com.ffa.back.models.MovieUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieUserGroupRepository extends JpaRepository<MovieUserGroup, Long> {
    List<MovieUserGroup> findByGroup(Group group);

    @Query("SELECT mug FROM MovieUserGroup mug WHERE mug.movie.id = :movieId AND mug.group.id IN :groupIds")
    List<MovieUserGroup> findByMovieIdAndGroupIds(Long movieId, List<Long> groupIds);
}
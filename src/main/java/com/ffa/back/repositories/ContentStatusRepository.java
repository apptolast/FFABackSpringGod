package com.ffa.back.repositories;

import com.ffa.back.models.ContentStatus;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentStatusRepository extends JpaRepository<ContentStatus, Long> {

    Optional<ContentStatus> findByMovieAndUserAndGroup(Movie movie, User user, Group group);
}

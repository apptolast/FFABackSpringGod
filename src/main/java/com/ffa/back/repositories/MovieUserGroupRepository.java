package com.ffa.back.repositories;

import com.ffa.back.models.Group;
import com.ffa.back.models.MovieUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieUserGroupRepository extends JpaRepository<MovieUserGroup, Long> {
    List<MovieUserGroup> findByGroup(Group group);
}
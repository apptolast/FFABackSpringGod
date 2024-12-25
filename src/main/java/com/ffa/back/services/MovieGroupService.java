package com.ffa.back.services;

import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class MovieGroupService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MovieUserGroupRepository movieUserGroupRepository;

    @Autowired
    private MovieRecommendationService recommendationService;

    @Transactional
    public void removeMovieFromGroup(Long movieId, Long groupId, User user) {
        MovieUserGroup movieUserGroup = movieUserGroupRepository
                .findByMovieIdAndGroupIdAndUserId(movieId, groupId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found in group"));

        movieUserGroupRepository.delete(movieUserGroup);
    }

    @Transactional
    public void addMovieToGroup(Long movieId, Long groupId, boolean toWatch, User user) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        MovieUserGroup movieUserGroup = new MovieUserGroup(movie, user, group, toWatch);
        movieUserGroupRepository.save(movieUserGroup);
    }
}

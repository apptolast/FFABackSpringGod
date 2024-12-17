package com.ffa.back.services;

import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import com.ffa.back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

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
    public void addMovieToGroups(Long movieId, List<Long> groupIds, boolean toWatch, User user) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        List<Group> groups = groupRepository.findAllById(groupIds);

        for (Group group : groups) {
            MovieUserGroup movieUserGroup = new MovieUserGroup(movie, user, group, toWatch);
            movieUserGroupRepository.save(movieUserGroup);

            // Actualizar recomendación
            Movie recommendedMovie = recommendationService.recommendMovie(group);
            group.setRecommendedMovie(recommendedMovie);
            groupRepository.save(group);
        }
    }
}

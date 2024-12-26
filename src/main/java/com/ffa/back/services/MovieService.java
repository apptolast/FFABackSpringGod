package com.ffa.back.services;

import com.ffa.back.dto.MovieReponseIDdto;
import com.ffa.back.models.Movie;
import com.ffa.back.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TmdbService tmdbService;

    public List<MovieReponseIDdto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::toMovieReponseIDdto)
                .collect(Collectors.toList());
    }


    public MovieReponseIDdto getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::toMovieReponseIDdto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    public MovieReponseIDdto getMovieByTmdbId(Long tmdbId) {
        return movieRepository.findByTmdbId(tmdbId)
                .map(this::toMovieReponseIDdto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        movieRepository.deleteById(id);
    }

    private MovieReponseIDdto toMovieReponseIDdto(Movie movie) {
        return new MovieReponseIDdto(
                movie.getId(),
                movie.getTmdbId(),
                movie.getTitle(),
                movie.getLanguage(),
                movie.getSynopsis(),
                movie.getImage(),
                movie.getAdult(),
                movie.getRelease_date(),
                movie.getVote_average(),
                movie.getVote_count(),
                movie.getGenre_ids()
        );
    }
}
package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
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

import java.sql.Date;
import java.util.ArrayList;
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

    @Transactional
    public Movie getOrCreateMovieByTmdbId(Long tmdbId) {
        return movieRepository.findByTmdbId(tmdbId)
                .orElseGet(() -> {
                    // Si no existe, obtener de TMDB y crear
                    JsonNode movieData = tmdbService.getDetails("movie", tmdbId.intValue()).block();
                    if (movieData == null) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Movie with TMDB ID %d not found", tmdbId));
                    }

                    Movie newMovie = new Movie();
                    newMovie.setTmdbId(tmdbId);
                    newMovie.setTitle(movieData.get("title").asText());
                    newMovie.setLanguage(movieData.get("original_language").asText());
                    newMovie.setSynopsis(movieData.get("overview").asText());
                    newMovie.setImage(movieData.get("poster_path").asText());
                    newMovie.setAdult(movieData.get("adult").asBoolean());
                    newMovie.setRelease_date(Date.valueOf(movieData.get("release_date").asText()));
                    newMovie.setVote_average(movieData.get("vote_average").asDouble());
                    newMovie.setVote_count(movieData.get("vote_count").asInt());

                    List<Integer> genreIds = new ArrayList<>();
                    JsonNode genresNode = movieData.get("genres");
                    if (genresNode != null && genresNode.isArray()) {
                        for (JsonNode genre : genresNode) {
                            genreIds.add(genre.get("id").asInt());
                        }
                    }
                    newMovie.setGenre_ids(genreIds);

                    return movieRepository.save(newMovie);
                });
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

    public List<MovieReponseIDdto> getMoviesByIds(List<Long> movieIds) {
        log.debug("Buscando películas por IDs: {}", movieIds);
        List<Movie> movies = movieRepository.findAllByIds(movieIds);
        log.debug("Encontradas {} películas", movies.size());
        return movies.stream()
                .map(this::toMovieReponseIDdto)
                .collect(Collectors.toList());
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
package com.ffa.back.services;

import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
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
    private GroupRepository groupRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    // Este método asume que el usuario que añade la película ya se ha autenticado y lo recibimos como parámetro
    // Si no lo tenemos, se puede obtener del contexto de seguridad.
    public void addMovieToGroups(Long movieId, List<Long> groupIds, boolean toWatch, User currentUser) {
        // Verificar que la película exista
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        // Verificar que los grupos existan
        List<Group> groups = groupRepository.findAllById(groupIds);
        if (groups.size() != groupIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more groups not found");
        }

        // Lógica: si toWatch = true, añadimos a la lista de "por ver" del usuario,
        // si false, añadimos a la lista de "vistas".
        if (toWatch) {
            List<Movie> porVer = currentUser.getPorVer();
            porVer.add(movie);
            currentUser.setPorVer(porVer);
        } else {
            List<Movie> vistas = currentUser.getVistas();
            vistas.add(movie);
            currentUser.setVistas(vistas);
        }

        // Guardamos el usuario con las nuevas relaciones
        userRepository.save(currentUser);

        // Opcionalmente, podríamos crear lógica adicional para reflejar la relación
        // con MovieUserGroup si fuera necesario. Por ahora, con las listas en usuario es suficiente.
    }
}

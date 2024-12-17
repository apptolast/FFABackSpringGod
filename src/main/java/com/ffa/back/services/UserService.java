package com.ffa.back.services;

import com.ffa.back.dto.MovieResponseDTO;
import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.dto.UserUpdateRequestDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Language;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toUserResponseDTO)
                .toList();
    }

    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toUserResponseDTO);
    }


    public Optional<UserResponseDTO> getUser(String email) {
        return userRepository.findByEmail(email)
                .map(this::toUserResponseDTO);
    }

    public Optional<UserResponseDTO> updateUser(Long id, UserUpdateRequestDTO userUpdateRequest) {
        return userRepository.findById(id).map(user -> {
            // Actualizar language si se proporciona
            if (userUpdateRequest.getLanguage() != null) {
                Optional<Language> langOpt = languageRepository.findByLanguage(userUpdateRequest.getLanguage());
                Language lang = langOpt.orElseGet(() -> languageRepository.save(new Language(userUpdateRequest.getLanguage())));
                user.setLanguage(lang);
            }
            // Actualizar email si se proporciona
            if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().isBlank()) {
                user.setEmail(userUpdateRequest.getEmail());
            }

            userRepository.save(user);
            return toUserResponseDTO(user);
        });
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        List<MovieResponseDTO> vistasDTO = toMovieResponseDTOList(user.getVistas());
        List<MovieResponseDTO> porVerDTO = toMovieResponseDTOList(user.getPorVer());
        List<Long> groupIds = user.getGroups().stream()
                .map(Group::getId)
                .toList();

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getLanguage() != null ? user.getLanguage().getLanguage() : null,
                vistasDTO,
                porVerDTO,
                groupIds
        );
    }

    private List<MovieResponseDTO> toMovieResponseDTOList(List<Movie> movies) {
        if (movies == null) return List.of();
        return movies.stream()
                .map(movie -> {
                    List<Long> groupIds = movie.getMovieUserGroups().stream()
                            .map(mug -> mug.getGroup().getId())
                            .distinct()
                            .toList();
                    return new MovieResponseDTO(groupIds, movie.getId());  // Ahora usa los parámetros correctos
                })
                .toList();
    }

}
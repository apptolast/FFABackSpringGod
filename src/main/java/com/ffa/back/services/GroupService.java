package com.ffa.back.services;

import com.ffa.back.dto.*;
import com.ffa.back.enums.MovieGroupStatus;
import com.ffa.back.models.*;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private MovieUserGroupRepository movieUserGroupRepository;

    public GroupResponseDTO createGroup(String name, User userfromtoken) {
        Group saved = new Group();
        saved.setName(name);
        saved.setOwner(userfromtoken);

        // Primero guardamos el grupo
        Group savedGroup = groupRepository.save(saved);
        log.debug("Grupo guardado con ID: {}", savedGroup.getId());

        // Creamos el GroupUser y lo añadimos a la lista del grupo
        GroupUser groupUser = new GroupUser(userfromtoken, savedGroup);
        savedGroup.getGroupUsers().add(groupUser);

        // Ahora guardamos nuevamente el grupo
        Group savedWithUser = groupRepository.save(savedGroup);
        log.debug("Grupo con usuario guardado. Cantidad de usuarios: {}", savedWithUser.getGroupUsers().size());

        return toGroupResponseDTO(savedWithUser);
    }

    public List<GroupResponseDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(this::toGroupResponseDTO)
                .toList();
    }

    public Optional<GroupResponseDTO> getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(this::toGroupResponseDTO);
    }

    public GroupResponseDTO updateGroup(Long id, GroupCreateRequestDTO groupDetails) {
        Group updated = groupRepository.findById(id).map(group -> {
            group.setName(groupDetails.getName());
            return groupRepository.save(group);
        }).orElseThrow(() -> new RuntimeException("Group not found"));

        return toGroupResponseDTO(updated);
    }

    public GroupResponseDTO addMemberToGroup(Long id, GroupMemberRequestDTO email) {
        // 1. Verificar que el grupo existe
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

        // 2. Verificar y obtener usuario (de Firebase y BD)
        User userToAdd = firebaseAuthService.verifyAndGetUserByEmail(email.getEmail());

        // 3. Verificar si el usuario ya está en el grupo
        boolean userAlreadyInGroup = group.getGroupUsers().stream()
                .anyMatch(groupUser -> groupUser.getUser().getId().equals(userToAdd.getId()));

        if (userAlreadyInGroup) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a member of this group");
        }

        // 4. Añadir el usuario al grupo
        GroupUser groupUser = new GroupUser(userToAdd, group);
        group.getGroupUsers().add(groupUser);
        Group updatedGroup = groupRepository.save(group);
        return toGroupResponseDTO(updatedGroup);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    // Métodos privados para mapear entidades a DTOs

    private GroupResponseDTO toGroupResponseDTO(Group group) {
        Long ownerId = (group.getOwner() != null) ? group.getOwner().getId() : null;

        List<UserResponseDTO> userDTOs = group.getGroupUsers().stream()
                .map(GroupUser::getUser)
                .map(this::toUserResponseDTO)
                .toList();

        // Agrupar películas vistas por movieId con sus respectivos usuarios
        List<MovieUsersDTO> watched = group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.FALSE.equals(mug.getToWatch()))
                .collect(Collectors.groupingBy(
                        mug -> mug.getMovie().getId(),
                        Collectors.mapping(
                                mug -> mug.getUser().getId(),
                                Collectors.toList()
                        )))
                .entrySet().stream()
                .map(entry -> new MovieUsersDTO(entry.getValue(), entry.getKey()))
                .toList();

        // Agrupar películas por ver por movieId con sus respectivos usuarios
        List<MovieUsersDTO> toWatch = group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.TRUE.equals(mug.getToWatch()))
                .collect(Collectors.groupingBy(
                        mug -> mug.getMovie().getId(),
                        Collectors.mapping(
                                mug -> mug.getUser().getId(),
                                Collectors.toList()
                        )))
                .entrySet().stream()
                .map(entry -> new MovieUsersDTO(entry.getValue(), entry.getKey()))
                .toList();

        MovieResponseDTO recommendedMovieDTO = null;
        if (group.getRecommendedMovie() != null) {
            // Obtener los IDs de los grupos donde está esta película
            List<Long> groupIds = group.getRecommendedMovie().getMovieUserGroups().stream()
                    .map(mug -> mug.getGroup().getId())
                    .distinct()
                    .toList();

            recommendedMovieDTO = new MovieResponseDTO(
                    groupIds,
                    group.getRecommendedMovie().getId()
            );
        }

        return new GroupResponseDTO(
                group.getId(),
                ownerId,
                group.getName(),
                userDTOs,
                watched,
                toWatch,
                recommendedMovieDTO
        );
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        List<MovieResponseDTO> vistas = toMovieResponseDTOList(user.getVistas());
        List<MovieResponseDTO> porVer = toMovieResponseDTOList(user.getPorVer());
        List<Long> groupIds = user.getGroups().stream()
                .map(Group::getId)
                .toList();

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getLanguage() != null ? user.getLanguage().getLanguage() : null,
                vistas,
                porVer,
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
                    return new MovieResponseDTO(groupIds, movie.getId());
                })
                .toList();
    }

    public MovieGroupStatusDTO getMovieGroupStatus(Long movieId, User user) {
        // Obtener los IDs de los grupos del usuario
        List<Long> userGroupIds = user.getGroups().stream()
                .map(Group::getId)
                .toList();

        // Obtener todas las relaciones MovieUserGroup para esta película en los grupos del usuario
        List<MovieUserGroup> movieGroups = movieUserGroupRepository.findByMovieIdAndGroupIds(movieId, userGroupIds);

        // Crear un mapa para fácil acceso a los MovieUserGroup por groupId
        Map<Long, List<MovieUserGroup>> groupMovieMap = movieGroups.stream()
                .collect(Collectors.groupingBy(mug -> mug.getGroup().getId()));

        // Procesar cada grupo del usuario
        List<GroupMovieStatusDTO> groupStatuses = userGroupIds.stream()
                .map(groupId -> {
                    List<MovieUserGroup> groupMovies = groupMovieMap.getOrDefault(groupId, List.of());
                    Optional<Group> group = groupRepository.findById(groupId);
                    String groupName = group.stream().map(
                            group1 -> {
                                return group1.getName();
                            }
                    ).toString();
                    MovieGroupStatus status = determineMovieStatus(groupMovies, user.getId());
                    return new GroupMovieStatusDTO(groupId, status, groupName);
                })
                .collect(Collectors.toList());

        return new MovieGroupStatusDTO(movieId, groupStatuses);
    }

    private MovieGroupStatus determineMovieStatus(List<MovieUserGroup> groupMovies, Long userId) {
        if (groupMovies.isEmpty()) {
            return MovieGroupStatus.NOT_IN_GROUP;
        }

        // Buscar si el usuario actual tiene la película en este grupo
        Optional<MovieUserGroup> userMovie = groupMovies.stream()
                .filter(mug -> mug.getUser().getId().equals(userId))
                .findFirst();

        if (userMovie.isPresent()) {
            // El usuario tiene la película
            return userMovie.get().getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_USER
                    : MovieGroupStatus.WATCHED_BY_USER;
        } else {
            // Otro usuario tiene la película
            return groupMovies.get(0).getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_OTHER
                    : MovieGroupStatus.WATCHED_BY_OTHER;
        }
    }
}

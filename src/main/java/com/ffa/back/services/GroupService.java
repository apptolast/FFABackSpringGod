package com.ffa.back.services;

import com.ffa.back.dto.*;
import com.ffa.back.models.*;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import com.ffa.back.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

        // Obtener las películas vistas y por ver del grupo
        List<MovieUserDTO> vistas = group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.FALSE.equals(mug.getToWatch()))
                .map(mug -> new MovieUserDTO(mug.getUser().getId(), mug.getMovie().getId()))
                .toList();

        List<MovieUserDTO> porVer = group.getMovieUserGroups().stream()
                .filter(mug -> Boolean.TRUE.equals(mug.getToWatch()))
                .map(mug -> new MovieUserDTO(mug.getUser().getId(), mug.getMovie().getId()))
                .toList();

        return new GroupResponseDTO(
                group.getId(),
                ownerId,
                group.getName(),
                userDTOs,
                vistas,
                porVer
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
                user.getFirebaseUuid(),
                user.getEmail(),
                user.getProvider(),
                user.getRole(),
                user.getSub(),
                user.getAuthTime(),
                user.getIat(),
                user.getExp(),
                user.getEmailVerified(),
                user.getSignInProvider(),
                user.getLanguage() != null ? user.getLanguage().getLanguage() : null,
                vistas,
                porVer,
                groupIds
        );
    }

    private List<MovieResponseDTO> toMovieResponseDTOList(List<Movie> movies) {
        if (movies == null) return List.of();
        return movies.stream()
                .map(movie -> new MovieResponseDTO(movie.getTitle(), movie.getId()))
                .toList();
    }
}

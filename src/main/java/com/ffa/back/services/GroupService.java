package com.ffa.back.services;

import com.ffa.back.dto.GroupResponseDTO;
import com.ffa.back.dto.MovieResponseDTO;
import com.ffa.back.dto.UserResponseDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.GroupUser;
import com.ffa.back.models.Movie;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

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

// Ahora guardamos nuevamente el grupo, no el groupUser por separado
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

    public GroupResponseDTO updateGroup(Long id, Group groupDetails) {
        Group updated = groupRepository.findById(id).map(group -> {
            group.setName(groupDetails.getName());
            group.setOwner(groupDetails.getOwner());
            return groupRepository.save(group);
        }).orElseThrow(() -> new RuntimeException("Group not found"));

        return toGroupResponseDTO(updated);
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

        return new GroupResponseDTO(
                group.getId(),
                ownerId,
                group.getName(),
                userDTOs
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

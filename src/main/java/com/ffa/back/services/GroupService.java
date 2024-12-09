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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public GroupResponseDTO createGroup(Group group) {
        Group saved = groupRepository.save(group);
        return toGroupResponseDTO(saved);
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
                porVer
        );
    }

    private List<MovieResponseDTO> toMovieResponseDTOList(List<Movie> movies) {
        if (movies == null) return List.of();
        return movies.stream()
                .map(movie -> new MovieResponseDTO(movie.getTitle(), movie.getId()))
                .toList();
    }
}

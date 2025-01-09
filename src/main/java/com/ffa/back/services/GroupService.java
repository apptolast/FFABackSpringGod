package com.ffa.back.services;

import com.ffa.back.dto.GroupCreateRequestDTO;
import com.ffa.back.dto.GroupMemberRequestDTO;
import com.ffa.back.dto.GroupResponseDTO;
import com.ffa.back.dto.MovieGroupStatusDTO;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;
    private final MovieService movieService;
    private final FirebaseAuthService firebaseAuthService;
    private final MovieUserGroupRepository movieUserGroupRepository;
    private final MovieRepository movieRepository;


    @Override
    public GroupResponseDTO createGroup(String name, User currentUser) {
        return null;
    }

    @Override
    public List<GroupResponseDTO> getAllGroups() {
        return List.of();
    }

    @Override
    public Optional<GroupResponseDTO> getGroupById(Long id) {
        return Optional.empty();
    }

    @Override
    public MovieGroupStatusDTO getMovieGroupStatus(Long movieId, User currentUser) {
        return null;
    }

    @Override
    public GroupResponseDTO updateGroup(Long id, GroupCreateRequestDTO groupDetails) {
        return null;
    }

    @Override
    public GroupResponseDTO addMemberToGroup(Long id, GroupMemberRequestDTO email) {
        return null;
    }

    @Override
    public void deleteGroup(Long id) {

    }

    @Override
    public Mono<MovieGroupStatusDTO> addMovieToGroup(Long movieId, Long groupId, boolean toWatch, User currentUser) {
        return null;
    }

    @Override
    public Mono<Void> removeMovieFromGroup(Long movieId, Long groupId, User currentUser) {
        return null;
    }
}

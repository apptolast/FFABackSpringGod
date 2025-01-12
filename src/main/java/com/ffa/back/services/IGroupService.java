package com.ffa.back.services;

import com.ffa.back.dto.GroupCreateRequestDTO;
import com.ffa.back.dto.GroupMemberRequestDTO;
import com.ffa.back.dto.GroupResponseDTO;
import com.ffa.back.dto.MovieGroupStatusDTO;
import com.ffa.back.models.User;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface IGroupService {

    GroupResponseDTO createGroup(String name, User currentUser);

    List<GroupResponseDTO> getAllGroups();

    Optional<GroupResponseDTO> getGroupById(Long id);

    GroupResponseDTO updateGroup(Long id, GroupCreateRequestDTO groupDetails);

    GroupResponseDTO addMemberToGroup(Long id, GroupMemberRequestDTO email);

    void deleteGroup(Long id);

}

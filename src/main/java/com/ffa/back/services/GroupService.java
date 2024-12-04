package com.ffa.back.services;

import com.ffa.back.dto.GroupCreateDTO;
import com.ffa.back.dto.GroupDTO;
import com.ffa.back.dto.GroupUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    GroupDTO createGroup(GroupCreateDTO groupCreateDTO);

    List<GroupDTO> getAllGroups();

    Optional<GroupDTO> getGroupById(Long id);

    Optional<GroupDTO> updateGroup(Long id, GroupUpdateDTO groupUpdateDTO);

    boolean deleteGroup(Long id);
}
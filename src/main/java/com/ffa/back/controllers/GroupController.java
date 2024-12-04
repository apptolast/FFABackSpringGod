package com.ffa.back.controllers;


import com.ffa.back.dto.GroupDTO;
import com.ffa.back.mappers.GroupMapper;
import com.ffa.back.models.Group;
import com.ffa.back.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("familyfilmapp/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupController(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = groups.stream()
                .map(groupMapper::toGroupDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupDTOs);
    }
}

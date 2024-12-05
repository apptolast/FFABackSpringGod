package com.ffa.back.controllers;


import com.ffa.back.dto.GroupDTO;
import com.ffa.back.mappers.GroupMapper;
import com.ffa.back.models.*;
import com.ffa.back.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("familyfilmapp/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final WatchListRepository watchListRepository;
    private final ViewListRepository viewListRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupController(GroupRepository groupRepository,
                           UserRepository userRepository,
                           MovieRepository movieRepository,
                           WatchListRepository watchListRepository,
                           ViewListRepository viewListRepository,
                           GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.watchListRepository = watchListRepository;
        this.viewListRepository = viewListRepository;
        this.groupMapper = groupMapper;
    }

    // Create a new group
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO, @RequestHeader("User-Id") Long userId) {
        Optional<User> ownerOpt = userRepository.findById(userId);
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setOwner(ownerOpt.get());

        // Add owner as a member
        group.setMembers(List.of(ownerOpt.get()));

        Group savedGroup = groupRepository.save(group);
        return ResponseEntity.ok(groupMapper.toGroupDTO(savedGroup));
    }

    // Get all groups for a user (both owned and member of)
    @GetMapping("/user")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@RequestHeader("User-Id") Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        List<Group> allGroups = new java.util.ArrayList<>();
        allGroups.addAll(user.getOwnedGroups());
        allGroups.addAll(user.getGroups());

        List<GroupDTO> groupDTOs = allGroups.stream()
                .distinct()
                .map(groupMapper::toGroupDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(groupDTOs);
    }

    // Update group name (only by owner)
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long groupId,
                                                @RequestBody GroupDTO groupDTO,
                                                @RequestHeader("User-Id") Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Group group = groupOpt.get();
        if (!group.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        group.setName(groupDTO.getName());
        Group updatedGroup = groupRepository.save(group);
        return ResponseEntity.ok(groupMapper.toGroupDTO(updatedGroup));
    }

    // Remove member from group (only by owner)
    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<?> removeMember(@PathVariable Long groupId,
                                          @PathVariable Long memberId,
                                          @RequestHeader("User-Id") Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Group group = groupOpt.get();
        if (!group.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        Optional<User> memberOpt = userRepository.findById(memberId);
        if (memberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        group.getMembers().remove(memberOpt.get());
        groupRepository.save(group);
        return ResponseEntity.ok().build();
    }

    // Delete group (only by owner)
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId,
                                         @RequestHeader("User-Id") Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Group group = groupOpt.get();
        if (!group.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        groupRepository.delete(group);
        return ResponseEntity.ok().build();
    }

    // Add movie to group's watch/view list
    @PostMapping("/{groupId}/movies/{movieId}")
    public ResponseEntity<?> addMovieToGroup(@PathVariable Long groupId,
                                             @PathVariable Long movieId,
                                             @RequestParam boolean isWatched,
                                             @RequestHeader("User-Id") Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (groupOpt.isEmpty() || movieOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Group group = groupOpt.get();
        Movie movie = movieOpt.get();
        User user = userOpt.get();

        // Verify user is member of the group
        if (!group.getMembers().contains(user) && !group.getOwner().equals(user)) {
            return ResponseEntity.status(403).build();
        }

        if (isWatched) {
            ViewListId viewListId = new ViewListId(groupId, movieId);
            ViewList viewList = new ViewList(viewListId, group, movie);
            viewListRepository.save(viewList);
        } else {
            WatchListId watchListId = new WatchListId(groupId, movieId);
            WatchList watchList = new WatchList(watchListId, group, movie);
            watchListRepository.save(watchList);
        }

        return ResponseEntity.ok().build();
    }
}

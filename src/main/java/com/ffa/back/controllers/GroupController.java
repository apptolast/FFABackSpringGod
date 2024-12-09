package com.ffa.back.controllers;

import com.ffa.back.dto.AddMovieToGroupRequestDTO;
import com.ffa.back.dto.GroupCreateRequestDTO;
import com.ffa.back.dto.GroupMemberRequestDTO;
import com.ffa.back.dto.GroupResponseDTO;
import com.ffa.back.models.User;
import com.ffa.back.repositories.UserRepository;
import com.ffa.back.services.GroupService;
import com.ffa.back.services.MovieGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("familyfilmapp/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MovieGroupService movieGroupService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public Mono<ResponseEntity<GroupResponseDTO>> createGroup(@RequestBody GroupCreateRequestDTO group, @org.springframework.security.core.annotation.AuthenticationPrincipal Mono<org.springframework.security.core.Authentication> authenticationMono) {
        return authenticationMono.flatMap(auth -> {
            String uid = auth.getName();
            return Mono.fromCallable(() -> {
                // Obtenemos el usuario actual a partir del uid
                User currentUser = userRepository.findByFirebaseUuid(uid)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

                GroupResponseDTO responseDTO = groupService.createGroup(group.getName(),
                        currentUser);
                return ResponseEntity.ok(responseDTO);
            });
        });
    }

    @GetMapping
    public Mono<ResponseEntity<List<GroupResponseDTO>>> getAllGroups() {
        return Mono.fromCallable(() -> ResponseEntity.ok(groupService.getAllGroups()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<GroupResponseDTO>> getGroupById(@PathVariable Long id) {
        return Mono.fromCallable(() ->
                groupService.getGroupById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build())
        );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<GroupResponseDTO>> updateGroup(@PathVariable Long id, @RequestBody GroupCreateRequestDTO groupDetails) {
        return Mono.fromCallable(() -> ResponseEntity.ok(groupService.updateGroup(id, groupDetails)));
    }

    @PutMapping("/addUser/{id}")
    public Mono<ResponseEntity<GroupResponseDTO>> addMemberToGroup(@PathVariable Long id, @RequestBody GroupMemberRequestDTO email) {
        return Mono.fromCallable(() -> ResponseEntity.ok(groupService.addMemberToGroup(id, email)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGroup(@PathVariable Long id) {
        return Mono.fromRunnable(() -> groupService.deleteGroup(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping("/addMovie")
    public Mono<ResponseEntity<String>> addMovieToGroups(@RequestBody AddMovieToGroupRequestDTO request,
                                                         @org.springframework.security.core.annotation.AuthenticationPrincipal Mono<org.springframework.security.core.Authentication> authenticationMono) {
        return authenticationMono.flatMap(auth -> {
            String uid = auth.getName();
            return Mono.fromCallable(() -> {
                // Obtenemos el usuario actual a partir del uid
                User currentUser = userRepository.findByFirebaseUuid(uid)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

                movieGroupService.addMovieToGroups(request.getMovieId(),
                        request.getGroupIds(),
                        request.isToWatch(),
                        currentUser);
                return ResponseEntity.ok("Movie added successfully");
            });
        });
    }


}



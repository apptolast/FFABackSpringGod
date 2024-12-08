package com.ffa.back.controllers;

import com.ffa.back.models.Group;
import com.ffa.back.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("familyfilmapp/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public Mono<ResponseEntity<Group>> createGroup(@RequestBody Group group) {
        return Mono.fromCallable(() -> ResponseEntity.ok(groupService.createGroup(group)));
    }

    @GetMapping
    public Mono<ResponseEntity<List<Group>>> getAllGroups() {
        return Mono.fromCallable(() -> ResponseEntity.ok(groupService.getAllGroups()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Group>> getGroupById(@PathVariable Long id) {
        return Mono.fromCallable(() ->
                groupService.getGroupById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build())
        );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Group>> updateGroup(@PathVariable Long id, @RequestBody Group groupDetails) {
        return Mono.fromCallable(() -> ResponseEntity.ok(groupService.updateGroup(id, groupDetails)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteGroup(@PathVariable Long id) {
        return Mono.fromRunnable(() -> groupService.deleteGroup(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}



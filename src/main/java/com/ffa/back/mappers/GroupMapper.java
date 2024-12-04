package com.ffa.back.mappers;

import com.ffa.back.dto.GroupCreateDTO;
import com.ffa.back.dto.GroupDTO;
import com.ffa.back.dto.GroupUpdateDTO;
import com.ffa.back.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "ownerId", expression = "java(mapOwnerToId(group.getOwner()))")
    @Mapping(target = "memberIds", expression = "java(mapMembersToIds(group.getMembers()))")
    @Mapping(target = "watchListIds", expression = "java(mapWatchListsToIds(group.getWatchLists()))")
    @Mapping(target = "viewListIds", expression = "java(mapViewListsToIds(group.getViewLists()))")
    @Mapping(target = "movieUserGroupIds", expression = "java(mapMovieUserGroupsToIds(group.getMovieUserGroups()))")
    GroupDTO toGroupDTO(Group group);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "watchLists", ignore = true)
    @Mapping(target = "viewLists", ignore = true)
    @Mapping(target = "movieUserGroups", ignore = true)
    Group toGroup(GroupDTO groupDTO);

    // Mapeo de DTO a Entidad para Creación
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "memberIds", target = "members")
    @Mapping(source = "watchListIds", target = "watchLists")
    @Mapping(source = "viewListIds", target = "viewLists")
    @Mapping(source = "movieUserGroupIds", target = "movieUserGroups")
    Group groupCreateDTOToGroup(GroupCreateDTO groupCreateDTO, @MappingTarget Group group);

    // Mapeo de DTO a Entidad para Actualización
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "memberIds", target = "members")
    @Mapping(source = "watchListIds", target = "watchLists")
    @Mapping(source = "viewListIds", target = "viewLists")
    @Mapping(source = "movieUserGroupIds", target = "movieUserGroups")
    void updateGroupFromDTO(GroupUpdateDTO groupUpdateDTO, @MappingTarget Group group);


    // Métodos auxiliares para mapear entidades a IDs

    default Long mapOwnerToId(User owner) {
        return owner != null ? owner.getId() : null;
    }

    default List<Long> mapMembersToIds(List<User> members) {
        if (members == null) {
            return null;
        }
        return members.stream().map(User::getId).collect(Collectors.toList());
    }

    default List<Long> mapWatchListsToIds(List<WatchList> watchLists) {
        if (watchLists == null) {
            return null;
        }
        return watchLists.stream()
                .map(watchList -> watchList.getId().getMovieId())
                .collect(Collectors.toList());
    }

    default List<Long> mapViewListsToIds(List<ViewList> viewLists) {
        if (viewLists == null) {
            return null;
        }
        return viewLists.stream()
                .map(viewList -> viewList.getId().getMovieId())
                .collect(Collectors.toList());
    }

    default List<Long> mapMovieUserGroupsToIds(List<MovieUserGroup> movieUserGroups) {
        if (movieUserGroups == null) {
            return null;
        }
        return movieUserGroups.stream()
                .map(movieUserGroup -> movieUserGroup.getId().getMovieId())
                .collect(Collectors.toList());
    }

    // Métodos para mapear IDs a entidades (para mapeo de DTO a Entidad)
    @Named("mapIdsToUsers")
    default List<User> mapIdsToUsers(List<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(id -> {
                    User user = new User();
                    user.setId(id);
                    return user;
                })
                .collect(Collectors.toList());
    }

    // Similarmente, puedes agregar métodos para mapear IDs a WatchList, ViewList, etc., si es necesario
}
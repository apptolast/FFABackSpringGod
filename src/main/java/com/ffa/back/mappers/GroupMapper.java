package com.ffa.back.mappers;

import com.ffa.back.dto.GroupDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.MovieUserGroupId;
import com.ffa.back.models.User;
import com.ffa.back.models.WatchList;
import com.ffa.back.models.ViewList;
import com.ffa.back.models.ViewListId;
import com.ffa.back.models.WatchListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    // Métodos auxiliares
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
}
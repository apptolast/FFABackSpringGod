package com.ffa.back.mappers;

import com.ffa.back.dto.GroupDTO;
import com.ffa.back.models.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, WatchListMapper.class, ViewListMapper.class, MovieUserGroupMapper.class})
public interface GroupMapper {

    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "members", source = "members")
    @Mapping(target = "watchLists", ignore = true) // Ignorar para evitar ciclos
    @Mapping(target = "viewLists", ignore = true)   // Ignorar para evitar ciclos
    @Mapping(target = "movieUserGroups", ignore = true)
        // Ignorar para evitar ciclos
    GroupDTO toGroupDTO(Group group);

    // Métodos inversos si es necesario
    // Group toGroup(GroupDTO groupDTO);
}
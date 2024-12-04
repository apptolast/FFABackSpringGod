package com.ffa.back.mappers;

import com.ffa.back.dto.GroupUserDTO;
import com.ffa.back.models.GroupUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GroupMapper.class})
public interface GroupUserMapper {

    // Mapear GroupUser a GroupUserDTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "group", target = "group")
    GroupUserDTO toGroupUserDTO(GroupUser groupUser);

    // Mapear GroupUserDTO a GroupUser
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "group", target = "group")
    GroupUser toGroupUser(GroupUserDTO groupUserDTO);
}
package com.ffa.back.mappers;

import com.ffa.back.dto.GroupUserDTO;
import com.ffa.back.models.GroupUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupUserMapper {

    GroupUserMapper INSTANCE = Mappers.getMapper(GroupUserMapper.class);

    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "id.groupId", target = "groupId")
    GroupUserDTO toGroupUserDTO(GroupUser groupUser);

    @Mapping(source = "userId", target = "id.userId")
    @Mapping(source = "groupId", target = "id.groupId")
    GroupUser toGroupUser(GroupUserDTO groupUserDTO);
}
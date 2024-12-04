package com.ffa.back.mappers;

import com.ffa.back.dto.GroupUserDTO;
import com.ffa.back.models.GroupUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GroupUserIdMapper.class})
public interface GroupUserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", expression = "java(groupUser.getUser() != null ? groupUser.getUser().getId() : null)")
    @Mapping(target = "groupId", expression = "java(groupUser.getGroup() != null ? groupUser.getGroup().getId() : null)")
    GroupUserDTO toGroupUserDTO(GroupUser groupUser);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "group", ignore = true)
    GroupUser toGroupUser(GroupUserDTO groupUserDTO);
}
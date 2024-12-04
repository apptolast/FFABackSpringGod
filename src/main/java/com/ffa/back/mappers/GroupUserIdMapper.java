package com.ffa.back.mappers;

import com.ffa.back.dto.GroupUserIdDTO;
import com.ffa.back.models.GroupUserId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupUserIdMapper {
    GroupUserIdDTO toGroupUserIdDTO(GroupUserId id);

    GroupUserId toGroupUserId(GroupUserIdDTO idDTO);
}
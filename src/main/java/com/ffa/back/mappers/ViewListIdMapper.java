package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListIdDTO;
import com.ffa.back.models.ViewListId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViewListIdMapper {
    ViewListIdDTO toViewListIdDTO(ViewListId id);

    ViewListId toViewListId(ViewListIdDTO idDTO);
}
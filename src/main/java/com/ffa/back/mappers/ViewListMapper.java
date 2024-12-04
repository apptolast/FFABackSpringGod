package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListDTO;
import com.ffa.back.dto.ViewListIdDTO;
import com.ffa.back.models.ViewList;
import com.ffa.back.models.ViewListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MovieMapper.class})
public interface ViewListMapper {

    // Mapear ViewList a ViewListDTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "movie", target = "movie")
    ViewListDTO toViewListDTO(ViewList viewList);

    // Mapear ViewListDTO a ViewList
    @Mapping(source = "id", target = "id")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "movie", target = "movie")
    ViewList toViewList(ViewListDTO viewListDTO);

    // Mapear ViewListId a ViewListIdDTO
    ViewListIdDTO toViewListIdDTO(ViewListId viewListId);

    // Mapear ViewListIdDTO a ViewListId
    ViewListId toViewListId(ViewListIdDTO viewListIdDTO);
}
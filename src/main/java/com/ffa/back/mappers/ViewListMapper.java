package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListDTO;
import com.ffa.back.models.ViewList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MovieMapper.class, ViewListIdMapper.class})
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
}
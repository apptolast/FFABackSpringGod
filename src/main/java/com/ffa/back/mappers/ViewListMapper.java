package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListDTO;
import com.ffa.back.dto.ViewListIdDTO;
import com.ffa.back.models.ViewList;
import com.ffa.back.models.ViewListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MovieMapper.class})
public interface ViewListMapper {

    ViewListMapper INSTANCE = Mappers.getMapper(ViewListMapper.class);

    @Mapping(source = "id.groupId", target = "groupId")
    @Mapping(source = "id.movieId", target = "movieId")
    ViewListDTO toViewListDTO(ViewList viewList);

    @Mapping(source = "groupId", target = "id.groupId")
    @Mapping(source = "movieId", target = "id.movieId")
    ViewList toViewList(ViewListDTO viewListDTO);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "movieId", target = "movieId")
    ViewListIdDTO toViewListIdDTO(ViewListId viewListId);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "movieId", target = "movieId")
    ViewListId toViewListId(ViewListIdDTO viewListIdDTO);
}
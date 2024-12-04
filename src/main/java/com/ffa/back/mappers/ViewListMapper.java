package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.ViewList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ViewListIdMapper.class})
public interface ViewListMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "groupId", expression = "java(viewList.getGroup() != null ? viewList.getGroup().getId() : null)")
    @Mapping(target = "movieId", expression = "java(viewList.getMovie() != null ? viewList.getMovie().getId() : null)")
    ViewListDTO toViewListDTO(ViewList viewList);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "movie", ignore = true)
    ViewList toViewList(ViewListDTO viewListDTO);
}
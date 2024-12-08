package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListCreateDTO;
import com.ffa.back.dto.ViewListDTO;
import com.ffa.back.dto.ViewListUpdateDTO;
import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.models.ViewList;
import com.ffa.back.models.WatchList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ViewListIdMapper.class})
public interface ViewListMapper {

    // Mapeo de Entidad a DTO
    ViewListDTO toViewListDTO(ViewList viewList);

    // Mapeo de DTO a Entidad para Creación
    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "movieId", target = "movie")
    ViewList viewListCreateDTOToViewList(ViewListCreateDTO viewListCreateDTO);

    // Mapeo de DTO a Entidad para Actualización
    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "movieId", target = "movie")
    void updateViewListFromDTO(ViewListUpdateDTO viewListUpdateDTO, @MappingTarget ViewList viewList);

}
package com.ffa.back.mappers;


import com.ffa.back.dto.WatchListCreateDTO;
import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.dto.WatchListUpdateDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.WatchList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {WatchListIdMapper.class})
public interface WatchListMapper {

    // Mapeo de Entidad a DTO
    WatchListDTO toWatchListDTO(WatchList watchList);

    // Mapeo de DTO a Entidad para Creación
    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "movieId", target = "movie")
    WatchList watchListCreateDTOToWatchList(WatchListCreateDTO watchListCreateDTO);

    // Mapeo de DTO a Entidad para Actualización
    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "movieId", target = "movie")
    void updateWatchListFromDTO(WatchListUpdateDTO watchListUpdateDTO, @MappingTarget WatchList watchList);
}
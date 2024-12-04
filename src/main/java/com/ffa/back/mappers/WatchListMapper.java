package com.ffa.back.mappers;


import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.models.WatchList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MovieMapper.class, WatchListIdMapper.class})
public interface WatchListMapper {

    // Mapear WatchList a WatchListDTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "movie", target = "movie")
    WatchListDTO toWatchListDTO(WatchList watchList);

    // Mapear WatchListDTO a WatchList
    @Mapping(source = "id", target = "id")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "movie", target = "movie")
    WatchList toWatchList(WatchListDTO watchListDTO);
}
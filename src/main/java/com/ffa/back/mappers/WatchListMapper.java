package com.ffa.back.mappers;

import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.dto.WatchListIdDTO;
import com.ffa.back.models.WatchList;
import com.ffa.back.models.WatchListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MovieMapper.class})
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

    // Mapear WatchListId a WatchListIdDTO
    WatchListIdDTO toWatchListIdDTO(WatchListId watchListId);

    // Mapear WatchListIdDTO a WatchListId
    WatchListId toWatchListId(WatchListIdDTO watchListIdDTO);
}
package com.ffa.back.mappers;

import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.dto.WatchListIdDTO;
import com.ffa.back.models.WatchList;
import com.ffa.back.models.WatchListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, MovieMapper.class})
public interface WatchListMapper {

    WatchListMapper INSTANCE = Mappers.getMapper(WatchListMapper.class);

    @Mapping(source = "id.groupId", target = "groupId")
    @Mapping(source = "id.movieId", target = "movieId")
    WatchListDTO toWatchListDTO(WatchList watchList);

    @Mapping(source = "groupId", target = "id.groupId")
    @Mapping(source = "movieId", target = "id.movieId")
    WatchList toWatchList(WatchListDTO watchListDTO);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "movieId", target = "movieId")
    WatchListIdDTO toWatchListIdDTO(WatchListId watchListId);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "movieId", target = "movieId")
    WatchListId toWatchListId(WatchListIdDTO watchListIdDTO);
}

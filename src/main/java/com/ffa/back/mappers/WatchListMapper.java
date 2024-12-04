package com.ffa.back.mappers;


import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.WatchList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WatchListIdMapper.class})
public interface WatchListMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "groupId", expression = "java(watchList.getGroup() != null ? watchList.getGroup().getId() : null)")
    @Mapping(target = "movieId", expression = "java(watchList.getMovie() != null ? watchList.getMovie().getId() : null)")
    WatchListDTO toWatchListDTO(WatchList watchList);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "movie", ignore = true)
    WatchList toWatchList(WatchListDTO watchListDTO);
}
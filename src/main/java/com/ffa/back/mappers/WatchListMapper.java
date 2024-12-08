package com.ffa.back.mappers;


import com.ffa.back.dto.WatchListCreateDTO;
import com.ffa.back.dto.WatchListDTO;
import com.ffa.back.dto.WatchListUpdateDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.WatchList;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {WatchListIdMapper.class})
public abstract class WatchListMapper {

    @Autowired
    protected GroupRepository groupRepository;

    @Autowired
    protected MovieRepository movieRepository;

    public abstract WatchListDTO toWatchListDTO(WatchList watchList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", expression = "java(mapGroup(watchListCreateDTO.getGroupId()))")
    @Mapping(target = "movie", expression = "java(mapMovie(watchListCreateDTO.getMovieId()))")
    public abstract WatchList watchListCreateDTOToWatchList(WatchListCreateDTO watchListCreateDTO);

    @Mapping(target = "group", expression = "java(mapGroup(watchListUpdateDTO.getGroupId()))")
    @Mapping(target = "movie", expression = "java(mapMovie(watchListUpdateDTO.getMovieId()))")
    public abstract void updateWatchListFromDTO(WatchListUpdateDTO watchListUpdateDTO, @MappingTarget WatchList watchList);

    // Métodos auxiliares
    protected Group mapGroup(Long groupId) {
        return groupId != null ? groupRepository.findById(groupId).orElse(null) : null;
    }

    protected Movie mapMovie(Long movieId) {
        return movieId != null ? movieRepository.findById(movieId).orElse(null) : null;
    }
}
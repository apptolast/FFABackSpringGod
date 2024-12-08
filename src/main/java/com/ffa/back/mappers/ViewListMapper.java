package com.ffa.back.mappers;

import com.ffa.back.dto.ViewListCreateDTO;
import com.ffa.back.dto.ViewListDTO;
import com.ffa.back.dto.ViewListUpdateDTO;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.ViewList;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ViewListIdMapper.class})
public abstract class ViewListMapper {

    @Autowired
    protected GroupRepository groupRepository;

    @Autowired
    protected MovieRepository movieRepository;

    public abstract ViewListDTO toViewListDTO(ViewList viewList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", expression = "java(mapGroup(viewListCreateDTO.getGroupId()))")
    @Mapping(target = "movie", expression = "java(mapMovie(viewListCreateDTO.getMovieId()))")
    public abstract ViewList viewListCreateDTOToViewList(ViewListCreateDTO viewListCreateDTO);

    @Mapping(target = "group", expression = "java(mapGroup(viewListUpdateDTO.getGroupId()))")
    @Mapping(target = "movie", expression = "java(mapMovie(viewListUpdateDTO.getMovieId()))")
    public abstract void updateViewListFromDTO(ViewListUpdateDTO viewListUpdateDTO, @MappingTarget ViewList viewList);

    // Métodos auxiliares
    protected Group mapGroup(Long groupId) {
        return groupId != null ? groupRepository.findById(groupId).orElse(null) : null;
    }

    protected Movie mapMovie(Long movieId) {
        return movieId != null ? movieRepository.findById(movieId).orElse(null) : null;
    }
}
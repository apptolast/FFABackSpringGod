package com.ffa.back.mappers;

import com.ffa.back.dto.WatchListIdDTO;
import com.ffa.back.models.WatchListId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WatchListIdMapper {
    WatchListIdDTO toWatchListIdDTO(WatchListId id);

    WatchListId toWatchListId(WatchListIdDTO idDTO);
}
package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieGroupStatusDTO {

    private Long movieId;
    private List<GroupMovieStatusDTO> groups;

}

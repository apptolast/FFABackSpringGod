package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieToGroupRequestDTO {
    private Long movieId;
    private Long groupId;
    private boolean toWatch; // true=para ver false = vistas
    private boolean addMovie; // true = por ver, false = vistas

}
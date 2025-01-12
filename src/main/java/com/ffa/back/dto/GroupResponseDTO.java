package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDTO {


    private Long id;
    private Long ownerId;
    private String name;
    private List<UserResponseDTO> users;
    private List<MovieUsersDTO> watched;
    private List<MovieUsersDTO> toWatch;
    private MovieResponseDTO recommendedMovie;

}

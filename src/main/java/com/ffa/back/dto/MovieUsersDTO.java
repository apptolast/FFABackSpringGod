package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieUsersDTO {

    private List<Long> usersId;
    private Long movieId;



}
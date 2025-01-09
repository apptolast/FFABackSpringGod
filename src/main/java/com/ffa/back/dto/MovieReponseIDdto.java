package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieReponseIDdto {

    private Long id;
    private Long tmdbId;
    private String title;
    private String language;
    private String synopsis;
    private String image;
    private Boolean adult;
    private Date releaseDate;
    private Double voteAverage;
    private Integer voteCount;
    private List<Integer> genreIds;


}

package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String language;
    private String synopsis;
    private String image;
    private Boolean adult;
    private Date release_date;
    private Double vote_average;
    private Integer vote_count;
    private List<Long> genreIds;
    private List<Long> userViewedIds;
    private List<Long> userWatchlistIds;
    private List<Long> watchListIds;
    private List<Long> viewListIds;
    private List<Long> movieUserGroupIds;


}

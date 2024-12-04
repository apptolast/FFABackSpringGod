package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieUserGroupDTO {
    private MovieUserGroupIdDTO id;
    private Long movieId;
    private Long userId;
    private Long groupId;
    private Boolean toWatch;

}

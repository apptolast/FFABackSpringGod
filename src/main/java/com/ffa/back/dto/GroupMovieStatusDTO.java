package com.ffa.back.dto;

import com.ffa.back.enums.MovieGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMovieStatusDTO {

    private Long groupId;
    private String groupName;
    private MovieGroupStatus status;

}

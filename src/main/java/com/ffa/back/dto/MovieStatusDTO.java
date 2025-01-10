package com.ffa.back.dto;


import com.ffa.back.enums.MovieField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieStatusDTO {
    private Long movieId;
    private Long groupId;
    private Long userId;
    private MovieField status;
}

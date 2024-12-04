package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewListDTO {
    private ViewListIdDTO id;
    private Long groupId;
    private Long movieId;

}

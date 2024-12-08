package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private Long ownerId;
    private List<Long> memberIds;
    private List<Long> watchListIds;
    private List<Long> viewListIds;
    private List<Long> movieUserGroupIds;
}


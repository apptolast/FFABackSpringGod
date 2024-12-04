package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserDTO {
    private GroupUserIdDTO id;
    private Long userId;
    private Long groupId;
}

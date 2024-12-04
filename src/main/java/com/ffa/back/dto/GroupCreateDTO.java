package com.ffa.back.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateDTO {
    @NotBlank(message = "El nombre del grupo no puede estar vacío")
    private String name;

    @NotNull(message = "El ID del propietario es obligatorio")
    private Long ownerId;

    private List<Long> memberIds;

    private List<Long> watchListIds;

    private List<Long> viewListIds;

    private List<Long> movieUserGroupIds;
}

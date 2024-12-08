package com.ffa.back.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchListCreateDTO {
    @NotNull(message = "El ID del grupo es obligatorio")
    private Long groupId;

    @NotNull(message = "El ID de la película es obligatorio")
    private Long movieId;
}
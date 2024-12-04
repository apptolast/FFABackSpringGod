package com.ffa.back.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {

    @Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2-letter code")
    private String language;

    private String email;

}

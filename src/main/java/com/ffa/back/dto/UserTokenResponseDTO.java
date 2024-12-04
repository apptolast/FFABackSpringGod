package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenResponseDTO {

    private String idToken;
    private String refreshToken;
    private String expiresIn;


}

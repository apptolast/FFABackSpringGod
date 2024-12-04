package com.ffa.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String firebaseUuid;
    private String email;
    private String provider;
    private String role;
    private String sub;
    private Long authTime;
    private Long iat;
    private Long exp;
    private Boolean emailVerified;
    private String signInProvider;
    private String language; // Manteniendo el language como String para simplicidad


}

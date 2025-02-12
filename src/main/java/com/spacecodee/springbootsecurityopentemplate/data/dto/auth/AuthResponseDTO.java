package com.spacecodee.springbootsecurityopentemplate.data.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class AuthResponseDTO {

    private String token;

    private Instant expiresAt;

    private String tokenType = "Bearer";

    private String username;

    private String role;
}

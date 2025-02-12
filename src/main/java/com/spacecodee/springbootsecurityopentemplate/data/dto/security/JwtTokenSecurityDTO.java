package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class JwtTokenSecurityDTO {

    private int id;

    private String token;

    private Instant expiryDate;

    private boolean isValid;

    private UserSecurityDTO userSecurityDTO;

    private String jti;

}

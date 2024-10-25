package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SecurityJwtTokenDTO {

    private int id;
    private String token;
    private Date expiryDate;
    private boolean isValid;
    private int userDetailsId;
}

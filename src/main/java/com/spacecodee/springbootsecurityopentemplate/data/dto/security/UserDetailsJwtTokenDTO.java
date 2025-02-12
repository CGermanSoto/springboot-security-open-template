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
public class UserDetailsJwtTokenDTO {

    private int id;
    private String token;
    private Date expiryDate;
    private boolean isValid;
    private UserDetailsDTO userDetailsDTO;
}

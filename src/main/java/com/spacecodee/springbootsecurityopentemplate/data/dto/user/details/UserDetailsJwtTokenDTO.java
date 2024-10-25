package com.spacecodee.springbootsecurityopentemplate.data.dto.user.details;

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

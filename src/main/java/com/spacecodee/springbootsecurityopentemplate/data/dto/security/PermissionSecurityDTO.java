package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionSecurityDTO {

    private int id;

    private OperationSecurityDTO operationDTO;

}
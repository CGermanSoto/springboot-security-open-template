package com.spacecodee.springbootsecurityopentemplate.service.security.operation;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityPathDTO;

import java.util.List;

public interface IOperationSecurityService {

    List<OperationSecurityPathDTO> findByPublicAccess();
}

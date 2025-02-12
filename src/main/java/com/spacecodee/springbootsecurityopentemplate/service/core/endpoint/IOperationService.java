package com.spacecodee.springbootsecurityopentemplate.service.core.endpoint;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;

import java.util.List;

public interface IOperationService {

    List<OperationSecurityDTO> findByPublicAccess();
}

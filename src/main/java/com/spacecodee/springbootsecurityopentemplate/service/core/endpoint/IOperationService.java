package com.spacecodee.springbootsecurityopentemplate.service.core.endpoint;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserDetailsOperationDTO;

import java.util.List;

public interface IOperationService {

    List<UserDetailsOperationDTO> findByPublicAccess();
}

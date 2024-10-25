package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsOperationDTO;

import java.util.List;

public interface IOperationService {

    List<UserDetailsOperationDTO> findByPublicAccess();
}

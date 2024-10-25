package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsOperationDTO;

import java.util.List;

public interface IOperationService {

    List<UserDetailsOperationDTO> findByPublicAccess();
}

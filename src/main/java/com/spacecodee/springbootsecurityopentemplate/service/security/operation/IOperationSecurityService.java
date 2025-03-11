package com.spacecodee.springbootsecurityopentemplate.service.security.operation;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityPathDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;

import java.util.List;

public interface IOperationSecurityService {

    List<OperationSecurityPathDTO> findByPublicAccess();

    /**
     * Extracts operation security DTOs from a user's security details
     *
     * @param userSecurityDTO The user security details
     * @return List of operation security DTOs
     */
    List<OperationSecurityDTO> extractUserOperations(UserSecurityDTO userSecurityDTO);
}

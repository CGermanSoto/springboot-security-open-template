package com.spacecodee.springbootsecurityopentemplate.service.security.operation.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityPathDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.mappers.security.IOperationSecurityMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.operation.IOperationSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.operation.IOperationSecurityService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OperationSecurityServiceImpl implements IOperationSecurityService {

    private final IOperationSecurityRepository operationRepository;
    private final IOperationSecurityMapper operationDTOMapper;

    @Override
    public List<OperationSecurityPathDTO> findByPublicAccess() {
        return this.operationRepository.findByPermitAllIsPublic()
                .stream()
                .map(this.operationDTOMapper::toOperationSecurityPathDTO)
                .toList();
    }

    @Override
    public List<OperationSecurityDTO> extractUserOperations(@NotNull UserSecurityDTO userSecurityDTO) {
        if (userSecurityDTO.getRoleSecurityDTO() == null ||
                userSecurityDTO.getRoleSecurityDTO().getPermissionDTOList() == null) {
            return Collections.emptyList();
        }

        return userSecurityDTO.getRoleSecurityDTO().getPermissionDTOList().stream()
                .filter(permission -> permission.getOperationDTO() != null)
                .map(permission -> this.operationDTOMapper.toOperationSecurityDTO(permission.getOperationDTO()))
                .toList();
    }
}

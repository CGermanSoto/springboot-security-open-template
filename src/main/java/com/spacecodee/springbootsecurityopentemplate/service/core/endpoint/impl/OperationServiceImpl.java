package com.spacecodee.springbootsecurityopentemplate.service.core.endpoint.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IOperationDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.core.IOperationRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.endpoint.IOperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OperationServiceImpl implements IOperationService {

    private final IOperationRepository operationRepository;
    private final IOperationDetailsMapper operationDTOMapper;

    @Override
    public List<OperationSecurityDTO> findByPublicAccess() {
        var list = this.operationRepository.findByPermitAllIsPublic();
        return list.stream()
                .map(this.operationDTOMapper::toUserDetailsOperationDTO)
                .toList();
    }
}

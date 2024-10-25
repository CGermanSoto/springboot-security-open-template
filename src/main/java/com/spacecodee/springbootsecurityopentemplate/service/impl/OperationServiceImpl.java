package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsOperationDTO;
import com.spacecodee.ticklyspace.mappers.IOperationMapper;
import com.spacecodee.ticklyspace.persistence.repository.IOperationRepository;
import com.spacecodee.ticklyspace.service.IOperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OperationServiceImpl implements IOperationService {

    private final IOperationRepository operationRepository;
    private final IOperationMapper operationDTOMapper;

    @Override
    public List<UserDetailsOperationDTO> findByPublicAccess() {
        var list = this.operationRepository.findByPermitAllIsPublic();
        return list.stream()
                .map(this.operationDTOMapper::toUserDetailsOperationDTO)
                .toList();
    }
}

package com.spacecodee.springbootsecurityopentemplate.service.core.operation;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.CreateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.OperationFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.UpdateOperationVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOperationService {

    OperationDTO createOperation(CreateOperationVO createOperationVO);

    OperationDTO updateOperation(UpdateOperationVO updateOperationVO);

    OperationDTO getOperationById(Integer id);

    OperationDetailDTO getOperationDetailById(Integer id);

    Page<OperationDTO> searchOperations(OperationFilterVO filterVO);

    List<OperationDTO> getAllOperationsByModuleId(Integer moduleId);

    List<OperationDTO> getAllOperations();

    void deleteOperation(Integer id);
}

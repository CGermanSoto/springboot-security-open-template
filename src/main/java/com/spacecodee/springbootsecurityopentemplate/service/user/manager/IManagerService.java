package com.spacecodee.springbootsecurityopentemplate.service.user.manager;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.CreateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.ManagerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.UpdateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface IManagerService {

    ManagerDTO createManager(CreateManagerVO createManagerVO);

    ManagerDTO updateManager(UpdateManagerVO updateManagerVO);

    ManagerDTO getManagerById(Integer id);

    ManagerDetailDTO getManagerDetailById(Integer id);

    UserEntity getManagerEntityById(Integer id);

    Page<ManagerDTO> searchManagers(ManagerFilterVO filterVO);

    ManagerDTO changeManagerStatus(Integer id, Boolean status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteManager(Integer id);

}

package com.spacecodee.springbootsecurityopentemplate.service.user.system.admin;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.CreateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.SystemAdminFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.UpdateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface ISystemAdminService {

    SystemAdminDTO createSystemAdmin(CreateSystemAdminVO createSystemAdminVO);

    SystemAdminDTO updateSystemAdmin(UpdateSystemAdminVO updateSystemAdminVO);

    SystemAdminDTO getSystemAdminById(Integer id);

    SystemAdminDetailDTO getSystemAdminDetailById(Integer id);

    UserEntity getSystemAdminEntityById(Integer id);

    Page<SystemAdminDTO> searchSystemAdmins(SystemAdminFilterVO filterVO);

    SystemAdminDTO changeSystemAdminStatus(Integer id, Boolean status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteSystemAdmin(Integer id);

}

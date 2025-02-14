package com.spacecodee.springbootsecurityopentemplate.service.user.developer;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.CreateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.UpdateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface IDeveloperService {

    DeveloperDTO createDeveloper(CreateDeveloperVO createDeveloperVO);

    DeveloperDTO updateDeveloper(UpdateDeveloperVO updateDeveloperVO);

    DeveloperDTO getDeveloperById(Integer id);

    DeveloperDetailDTO getDeveloperDetailById(Integer id);

    UserEntity getDeveloperEntityById(Integer id);

    Page<DeveloperDTO> searchDevelopers(DeveloperFilterVO filterVO);

    DeveloperDTO changeDeveloperStatus(Integer id, Boolean status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteDeveloper(Integer id);
}

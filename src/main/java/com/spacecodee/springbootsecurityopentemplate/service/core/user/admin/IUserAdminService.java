package com.spacecodee.springbootsecurityopentemplate.service.core.user.admin;

import java.util.List;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.AdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;

public interface IUserAdminService {

    void add(AdminAVO adminAVO, String locale);

    void update(int id, AdminUVO adminVO, String locale);

    void delete(int id, String locale);

    AdminDTO findById(int id, String locale);

    List<AdminDTO> findAll(String locale);
}

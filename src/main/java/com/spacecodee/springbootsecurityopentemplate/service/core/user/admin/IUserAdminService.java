package com.spacecodee.springbootsecurityopentemplate.service.core.user.admin;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;

public interface IUserAdminService {

    void add(AdminAVO adminAVO, String locale);

    void update(int id, AdminUVO adminAVO, String locale);

    void delete(int id, String locale);
}

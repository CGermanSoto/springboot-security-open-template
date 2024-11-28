package com.spacecodee.springbootsecurityopentemplate.service.core.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminUVO;

public interface IUserAdminService {

    void add(AdminAVO adminAVO, String locale);

    void update(int id, AdminUVO adminAVO, String locale);

    void delete(int id, String locale);
}

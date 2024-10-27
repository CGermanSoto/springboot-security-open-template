package com.spacecodee.springbootsecurityopentemplate.service.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminVO;

public interface IUserAdminService {

    void add(AdminVO adminVO, String locale);

    void update(int id, AdminVO adminVO, String locale);

    void delete(int id, String locale);
}

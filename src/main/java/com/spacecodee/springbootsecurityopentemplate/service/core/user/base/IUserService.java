package com.spacecodee.springbootsecurityopentemplate.service.core.user.base;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;

public interface IUserService {

    void addUser(AdminAVO request, String lang);
}

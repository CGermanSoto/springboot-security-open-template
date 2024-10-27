package com.spacecodee.springbootsecurityopentemplate.service.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminVO;

public interface IUserService {

    void addUser(AdminVO request, String lang);
}

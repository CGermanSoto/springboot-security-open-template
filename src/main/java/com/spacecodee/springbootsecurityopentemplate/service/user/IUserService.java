package com.spacecodee.springbootsecurityopentemplate.service.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.AddAdminVO;

public interface IUserService {

    void addUser(AddAdminVO request, String lang);
}

package com.spacecodee.springbootsecurityopentemplate.service.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.AddAdminVO;

public interface IUserAdminService {

    void add(AddAdminVO adminVO, String locale);
}

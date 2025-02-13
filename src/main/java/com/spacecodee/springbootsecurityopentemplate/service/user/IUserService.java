package com.spacecodee.springbootsecurityopentemplate.service.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserProfileDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;

public interface IUserService {

    UserEntity getUserEntityById(int id);

    UserProfileDTO getUserProfile(int id);

}

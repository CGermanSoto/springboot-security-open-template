package com.spacecodee.springbootsecurityopentemplate.controller.api.user.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.IUserController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserProfileDTO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/security")
public class UserControllerImpl extends AbstractController implements IUserController {

    private final IUserService userService;

    public UserControllerImpl(MessageParameterHandler messageParameterHandler, IUserService userService) {
        super(messageParameterHandler);
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<UserProfileDTO>> getUserProfile(Integer id) {
        UserProfileDTO userProfile = this.userService.getUserProfile(id);
        return ResponseEntity.ok(super.createDataResponse(
                userProfile,
                "user.profile.found.success",
                HttpStatus.OK,
                userProfile.getUsername()));
    }

}

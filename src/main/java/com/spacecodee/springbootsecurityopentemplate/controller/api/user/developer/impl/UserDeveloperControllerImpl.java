package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.IUserDeveloperController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.developer.IUserDeveloperService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-developer")
public class UserDeveloperControllerImpl extends AbstractController implements IUserDeveloperController {
    private final IUserDeveloperService userDeveloperService;

    public UserDeveloperControllerImpl(MessageUtilComponent messageUtilComponent,
                                       MessageParameterHandler messageParameterHandler,
                                       IUserDeveloperService userDeveloperService) {
        super(messageUtilComponent, messageParameterHandler);
        this.userDeveloperService = userDeveloperService;
    }

}
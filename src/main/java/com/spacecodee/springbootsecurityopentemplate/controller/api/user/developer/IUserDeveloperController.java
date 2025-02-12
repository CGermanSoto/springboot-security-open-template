// IUserDeveloperController.java
package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Developer Management", description = "APIs for managing developer users")
@SecurityRequirement(name = "bearerAuth")
public interface IUserDeveloperController {

}
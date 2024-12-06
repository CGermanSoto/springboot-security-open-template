package com.spacecodee.springbootsecurityopentemplate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Spring Security JWT Template API", version = "1.0", description = "REST API for managing users, roles and permissions", contact = @Contact(name = "spacecodee", email = "spacecodee@gmail.com", url = "https://github.com/spacecodee"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")), servers = {
        @Server(url = "/api/v1", description = "Default Server URL")
})
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfig {
}
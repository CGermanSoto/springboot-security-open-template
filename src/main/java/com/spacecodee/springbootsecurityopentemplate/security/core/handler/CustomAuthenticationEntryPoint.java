package com.spacecodee.springbootsecurityopentemplate.security.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiErrorPojo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApiErrorPojo apiErrorPojo = new ApiErrorPojo();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        this.apiErrorPojo.setBackendMessage(authException.getLocalizedMessage());
        this.apiErrorPojo.setMessage("There are no credentials for this authentication, please log in to access this resource.");
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        var apiErrorAsJson = objectMapper.writeValueAsString(this.apiErrorPojo);

        response.getWriter().write(apiErrorAsJson);
    }
}
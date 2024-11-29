package com.spacecodee.springbootsecurityopentemplate.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorPojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageUtilComponent messageUtilComponent;
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Override
    public void commence(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull AuthenticationException authException) throws IOException {

        log.warn("Authentication failed for request: {}", request.getRequestURI());

        var errorResponse = ApiErrorPojo.of(
                authException.getLocalizedMessage(),
                messageUtilComponent.getMessage("auth.credentials.required", "en"),
                request.getRequestURI(),
                request.getMethod());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
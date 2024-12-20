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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

        private final MessageUtilComponent messageUtilComponent;
        private static final ObjectMapper objectMapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule());

        @Override
        public void handle(
                        @NotNull HttpServletRequest request,
                        @NotNull HttpServletResponse response,
                        @NotNull AccessDeniedException accessDeniedException) throws IOException {

                // Get authenticated user
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication != null ? authentication.getName() : "anonymous";

                log.warn("Access denied to resource: {} for user: {}", request.getRequestURI(), username);

                var errorResponse = ApiErrorPojo.of(
                                accessDeniedException.getLocalizedMessage(),
                                messageUtilComponent.getMessage("auth.access.denied",
                                                request.getLocale().toString(),
                                                username), // Pass username as parameter
                                request.getRequestURI(),
                                request.getMethod());

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
}
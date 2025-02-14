package com.spacecodee.springbootsecurityopentemplate.security.path.impl;

import com.spacecodee.springbootsecurityopentemplate.constants.SecurityConstants;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityPathDTO;
import com.spacecodee.springbootsecurityopentemplate.security.path.ISecurityPathService;
import com.spacecodee.springbootsecurityopentemplate.service.security.operation.IOperationSecurityService;
import com.spacecodee.springbootsecurityopentemplate.utils.PathUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityPathServiceImpl implements ISecurityPathService {

    private final IOperationSecurityService operationSecurityService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private List<String> publicPaths = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.updatePublicPaths();
    }

    private void updatePublicPaths() {
        this.publicPaths = this.operationSecurityService.findByPublicAccess()
                .stream()
                .map(OperationSecurityPathDTO::getFullPath)
                .toList();
        log.info("Public paths updated: {}", this.publicPaths);
    }

    @Override
    public boolean isPublicPath(@NotNull String path) {
        String normalizedPath = PathUtils.normalizeRequestPath(path);
        return this.publicPaths.stream()
                .anyMatch(publicPath -> this.pathMatcher.match(publicPath, normalizedPath));
    }

    @Override
    public boolean isSwaggerPath(@NotNull String path) {
        return SecurityConstants.SWAGGER_PATHS
                .stream()
                .anyMatch(path::contains);
    }

    @Override
    public boolean isErrorPath(@NotNull String path) {
        return SecurityConstants.ERROR_PATHS
                .stream()
                .anyMatch(errorPath -> this.pathMatcher.match(errorPath, PathUtils.normalizeRequestPath(path)));
    }

    @Override
    public boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return this.isSwaggerPath(path) ||
                this.isPublicPath(path) ||
                this.isErrorPath(path);
    }

    @Override
    public boolean isRefreshTokenPath(@NotNull String path) {
        return path.endsWith(SecurityConstants.REFRESH_TOKEN_PATH);
    }
    
}
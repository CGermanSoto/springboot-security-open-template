package com.spacecodee.springbootsecurityopentemplate.security.path.impl;

import com.spacecodee.springbootsecurityopentemplate.cache.ISecurityCacheService;
import com.spacecodee.springbootsecurityopentemplate.constants.SecurityConstants;
import com.spacecodee.springbootsecurityopentemplate.security.path.ISecurityPathService;
import com.spacecodee.springbootsecurityopentemplate.utils.PathUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityPathServiceImpl implements ISecurityPathService {

    private final ISecurityCacheService securityCacheService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean isPublicPath(@NotNull String path) {
        String normalizedPath = PathUtils.normalizeRequestPath(path);
        List<String> publicPaths = this.securityCacheService.getPublicPaths();
        return publicPaths.stream()
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
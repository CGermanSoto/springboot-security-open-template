package com.spacecodee.springbootsecurityopentemplate.security.path;

import jakarta.servlet.http.HttpServletRequest;

public interface ISecurityPathService {

    boolean isPublicPath(String path);

    boolean isSwaggerPath(String path);

    boolean shouldNotFilter(HttpServletRequest request);

    boolean isErrorPath(String path);

    boolean isRefreshTokenPath(String path);

}

package com.spacecodee.springbootsecurityopentemplate.security.util;

public final class SecurityConstants {
    private SecurityConstants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "authorities";
    public static final String USERNAME_KEY = "username";
    public static final String LANG_HEADER = "Accept-Language";
    public static final long TOKEN_EXPIRATION = 86400000; // 24 hours
}
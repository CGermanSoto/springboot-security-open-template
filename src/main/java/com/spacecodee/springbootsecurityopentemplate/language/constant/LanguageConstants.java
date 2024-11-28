package com.spacecodee.springbootsecurityopentemplate.language.constant;

import java.util.List;

public final class LanguageConstants {
    private LanguageConstants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DEFAULT_LOCALE = "en";
    public static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    private static final List<String> MESSAGE_SOURCES = List.of(
            "classpath:locale/message",
            "classpath:api/api-doc",
            "classpath:validation");

    public static List<String> getMessageSources() {
        return MESSAGE_SOURCES;
    }
}
package com.spacecodee.springbootsecurityopentemplate.exceptions.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MessageParameterUtil {
    private MessageParameterUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatMessage(MessageSource messageSource, String messageKey, Object... params) {
        try {
            return messageSource.getMessage(
                    messageKey,
                    params,
                    LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.error("Error formatting message: {}", e.getMessage());
            return messageKey;
        }
    }
}
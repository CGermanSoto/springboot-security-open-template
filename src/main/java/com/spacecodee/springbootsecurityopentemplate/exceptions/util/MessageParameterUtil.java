package com.spacecodee.springbootsecurityopentemplate.exceptions.util;

import java.util.Arrays;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MessageParameterUtil {
    private MessageParameterUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatMessage(MessageSource messageSource, String messageKey, Object... params) {
        validateMessageKey(messageKey);
        Object[] validatedParams = validateAndCleanParams(params);

        try {
            var locale = LocaleContextHolder.getLocale();
            log.debug("Formatting message: key='{}', locale='{}', params='{}'",
                    messageKey, locale, Arrays.toString(validatedParams));

            String message = messageSource.getMessage(messageKey, validatedParams, locale);
            log.debug("Formatted message result: '{}'", message);
            return message;
        } catch (Exception e) {
            log.error("Error formatting message: key='{}', params='{}', error='{}'",
                    messageKey, Arrays.toString(params), e.getMessage());
            return messageKey;
        }
    }

    private static void validateMessageKey(String messageKey) {
        if (messageKey == null || messageKey.isBlank()) {
            String error = "Message key cannot be null or empty";
            log.error(error);
            throw new IllegalArgumentException(error);
        }
    }

    public static Object[] validateAndCleanParams(Object... params) {
        if (params == null) {
            return new Object[0];
        }

        return Arrays.stream(params)
                .map(param -> {
                    if (param == null) {
                        log.warn("Null parameter detected, replacing with empty string");
                        return "";
                    }
                    if (param.toString().isBlank()) {
                        log.warn("Blank parameter detected: {}", param.getClass().getSimpleName());
                    }
                    return param;
                })
                .toArray();
    }
}
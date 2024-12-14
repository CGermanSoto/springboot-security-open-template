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
            return messageSource.getMessage(
                    messageKey,
                    validatedParams,
                    LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.error("Error formatting message: {} with params: {}", messageKey, Arrays.toString(params), e);
            return messageKey;
        }
    }

    private static void validateMessageKey(String messageKey) {
        if (messageKey == null || messageKey.isBlank()) {
            log.error("Message key is null or empty");
            throw new IllegalArgumentException("Message key cannot be null or empty");
        }
    }

    private static Object[] validateAndCleanParams(Object... params) {
        if (params == null) {
            return new Object[0];
        }

        return Arrays.stream(params)
                .map(param -> param == null ? "" : param)
                .peek(param -> {
                    if (param.toString().isBlank()) {
                        log.warn("Empty parameter detected, using empty string");
                    }
                })
                .toArray();
    }
}
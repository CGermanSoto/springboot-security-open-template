package com.spacecodee.springbootsecurityopentemplate.language;

import com.spacecodee.springbootsecurityopentemplate.language.constant.LanguageConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageUtilComponent {

    private final MessageSource messageSource;
    private static final Map<String, Locale> localeCache = new ConcurrentHashMap<>();

    public String getMessage(String message, String locale) {
        return getMessageSource().getMessage(message, null, getLocaleApp(locale));
    }

    public String getMessage(String message, String locale, Object... args) {
        return getMessageSource().getMessage(message, args, getLocaleApp(locale));
    }

    public String getMessageWithFallback(String message, String locale, String defaultMessage) {
        return getMessageSource().getMessage(message, null, defaultMessage, getLocaleApp(locale));
    }

    private Locale getLocaleApp(String locale) {
        if (locale == null || locale.isEmpty()) {
            return Locale.forLanguageTag(LanguageConstants.DEFAULT_LOCALE);
        }

        return localeCache.computeIfAbsent(locale.toLowerCase(), key -> {
            try {
                var loc = Locale.forLanguageTag(key);
                if (isSupportedLocale(loc)) {
                    return loc;
                }
            } catch (Exception e) {
                log.warn("Invalid locale: {}. Using default locale.", key);
            }
            return Locale.forLanguageTag(LanguageConstants.DEFAULT_LOCALE);
        });
    }

    public boolean isSupportedLocale(@NotNull Locale locale) {
        return Arrays.asList("en", "es").contains(locale.getLanguage());
    }

}

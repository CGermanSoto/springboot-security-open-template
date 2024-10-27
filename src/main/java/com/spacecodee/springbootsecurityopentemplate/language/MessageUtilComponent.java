package com.spacecodee.springbootsecurityopentemplate.language;

import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
public class MessageUtilComponent {

    private final MessageSource messageSource;

    public MessageUtilComponent(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String message, String locale) {
        return getMessageSource().getMessage(message, null, this.getLocaleApp(locale));
    }

    private Locale getLocaleApp(String locale) {
        if (locale == null || locale.isEmpty()) {
            return Locale.ENGLISH; // Default to English if locale is null or empty
        }
        return locale.equalsIgnoreCase("en") ? Locale.ENGLISH : Locale.forLanguageTag(locale);
    }
}

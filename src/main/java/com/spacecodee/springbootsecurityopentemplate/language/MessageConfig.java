package com.spacecodee.springbootsecurityopentemplate.language;

import com.spacecodee.springbootsecurityopentemplate.language.constant.LanguageConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;
import java.util.Properties;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                LanguageConstants.getMessageSources().toArray(new String[0]));
        messageSource.setDefaultEncoding(LanguageConstants.DEFAULT_ENCODING);
        messageSource.setCacheSeconds(3600); // Cache for 1 hour
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setAlwaysUseMessageFormat(true);

        // English messages are in message.properties
        messageSource.setDefaultLocale(Locale.ENGLISH);

        // Add debug properties
        var commonMessages = new Properties();
        commonMessages.put("defaultLocale", "en");
        commonMessages.put("currentBasenames", String.join(", ", LanguageConstants.getMessageSources()));
        messageSource.setCommonMessages(commonMessages);

        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        var bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}

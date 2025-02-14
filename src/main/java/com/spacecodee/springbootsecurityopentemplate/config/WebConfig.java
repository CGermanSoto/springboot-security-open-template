package com.spacecodee.springbootsecurityopentemplate.config;

import com.spacecodee.springbootsecurityopentemplate.config.converter.StringToMapConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        registry.addConverter(new StringToMapConverter());
    }
}
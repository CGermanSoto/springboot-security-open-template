// LocaleResolverFilter.java
package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.language.constant.LanguageConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public class LocaleResolverFilter extends OncePerRequestFilter {

    private static final ThreadLocal<String> currentLocale = new ThreadLocal<>();
    private final MessageUtilComponent messageUtilComponent;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String locale = extractAndValidateLocale(request);
            log.debug("Setting locale: {} for request: {}", locale, request.getRequestURI());
            currentLocale.set(locale);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error processing locale: {}", e.getMessage());
            throw e;
        } finally {
            currentLocale.remove();
        }
    }

    private String extractAndValidateLocale(@NotNull HttpServletRequest request) {
        String locale = request.getHeader(LanguageConstants.ACCEPT_LANGUAGE_HEADER);
        log.debug("Extracted locale from header: {}", locale);

        if (locale == null || locale.isEmpty()) {
            log.debug("No locale found in header, using default: {}", LanguageConstants.DEFAULT_LOCALE);
            return LanguageConstants.DEFAULT_LOCALE;
        }

        if (!messageUtilComponent.isSupportedLocale(Locale.forLanguageTag(locale))) {
            log.warn("Unsupported locale: {}, falling back to default: {}", locale, LanguageConstants.DEFAULT_LOCALE);
            return LanguageConstants.DEFAULT_LOCALE;
        }

        return locale.toLowerCase();
    }

    public static String getCurrentLocale() {
        String locale = currentLocale.get();
        if (locale == null) {
            log.debug("No locale in ThreadLocal, using default: {}", LanguageConstants.DEFAULT_LOCALE);
            return LanguageConstants.DEFAULT_LOCALE;
        }
        return locale;
    }
}
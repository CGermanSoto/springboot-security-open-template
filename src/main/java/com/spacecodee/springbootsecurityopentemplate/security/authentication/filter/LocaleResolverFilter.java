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
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String locale = extractAndValidateLocale(request);
            currentLocale.set(locale);

            log.debug("Setting locale: {} for request: {}", locale, request.getRequestURI());
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
        return currentLocale.get() != null ? currentLocale.get() : LanguageConstants.DEFAULT_LOCALE;
    }
}
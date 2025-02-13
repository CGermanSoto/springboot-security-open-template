package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.spacecodee.springbootsecurityopentemplate.exceptions.locale.LocaleResolutionException;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.language.constant.LanguageConstants;
import com.spacecodee.springbootsecurityopentemplate.security.path.ISecurityPathService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cache.ISecurityCacheService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Locale;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public class LocaleResolverFilter extends OncePerRequestFilter {

    private final MessageUtilComponent messageUtilComponent;
    private final ISecurityPathService securityPathService;
    private final LocaleResolver localeResolver;
    private final ISecurityCacheService securityCacheService;
    private static final String DEFAULT_LOCALE = LanguageConstants.DEFAULT_LOCALE;

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return this.securityPathService.shouldNotFilter(request);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            this.handleLocaleResolution(request, filterChain, response);
        } catch (LocaleResolutionException e) {
            this.handleLocaleError(request, response, filterChain, e);
        } finally {
            this.cleanupLocaleContext();
        }
    }

    private void handleLocaleResolution(
            HttpServletRequest request,
            @NotNull FilterChain filterChain,
            HttpServletResponse response) throws ServletException, IOException {

        String resolvedLocale = this.resolveLocale(request);
        LocaleContextHolder.setLocale(Locale.forLanguageTag(resolvedLocale));
        filterChain.doFilter(request, response);
    }

    private @NotNull String resolveLocale(@NotNull HttpServletRequest request) {
        String localeKey = request.getHeader("Accept-Language");

        return this.securityCacheService.getCachedLocale(
                localeKey,
                () -> {
                    Locale resolvedLocale = this.localeResolver.resolveLocale(request);
                    if (!this.isValidLocale(resolvedLocale.toString())) {
                        resolvedLocale = Locale.forLanguageTag(DEFAULT_LOCALE);
                    }
                    return resolvedLocale;
                }).toString().toLowerCase();
    }

    private boolean isValidLocale(String locale) {
        return locale != null &&
                !locale.isEmpty() &&
                this.messageUtilComponent.isSupportedLocale(Locale.forLanguageTag(locale));
    }

    private void handleLocaleError(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            @NotNull LocaleResolutionException e) throws ServletException, IOException {

        log.error("Locale resolution error: {}", e.getMessage());

        if (this.shouldNotFilter(request)) {
            this.useDefaultLocale(filterChain, request, response);
            return;
        }

        throw e;
    }

    private void useDefaultLocale(
            @NotNull FilterChain filterChain,
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(DEFAULT_LOCALE));
        filterChain.doFilter(request, response);
    }

    private void cleanupLocaleContext() {
        LocaleContextHolder.resetLocaleContext();
    }
}
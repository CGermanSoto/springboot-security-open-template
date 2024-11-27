// LocaleResolverFilter.java
package com.spacecodee.springbootsecurityopentemplate.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LocaleResolverFilter extends OncePerRequestFilter {

    private static final ThreadLocal<String> currentLocale = new ThreadLocal<>();

    public static String getCurrentLocale() {
        return currentLocale.get() != null ? currentLocale.get() : "en";
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String locale = request.getHeader("Accept-Language");
            currentLocale.set(locale != null ? locale : "en");
            filterChain.doFilter(request, response);
        } finally {
            currentLocale.remove();
        }
    }
}
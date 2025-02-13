package com.spacecodee.springbootsecurityopentemplate.security.config;

import com.spacecodee.springbootsecurityopentemplate.security.authentication.filter.JwtAuthenticationFilter;
import com.spacecodee.springbootsecurityopentemplate.security.authentication.filter.LocaleResolverFilter;
import com.spacecodee.springbootsecurityopentemplate.security.authentication.filter.RateLimitFilter;
import com.spacecodee.springbootsecurityopentemplate.security.authorization.manager.CustomAuthorizationManager;
import com.spacecodee.springbootsecurityopentemplate.security.handler.CustomAccessDeniedHandler;
import com.spacecodee.springbootsecurityopentemplate.security.handler.CustomAuthenticationEntryPoint;
import com.spacecodee.springbootsecurityopentemplate.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final LocaleResolverFilter localeResolverFilter;
    private final CustomAuthorizationManager authorizationManager;
    private final RateLimitFilter rateLimitFilter;
    private final CustomSecurityHeadersConfigurer securityHeadersConfigurer;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .with(this.securityHeadersConfigurer, Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/swagger-resources/**").permitAll();
                    auth.anyRequest().access(authorizationManager);
                })
                .addFilterBefore(this.localeResolverFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(this.jwtAuthenticationFilter, LocaleResolverFilter.class)
                .addFilterAt(this.rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(this.authenticationEntryPoint)
                        .accessDeniedHandler(this.accessDeniedHandler))
                .formLogin(form -> form.successHandler(this.authenticationSuccessHandler))
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

package com.spacecodee.springbootsecurityopentemplate.security.core;

import com.spacecodee.ticklyspace.service.user.details.IUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Configuration
public class SecurityBeansInjector {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final IUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationStrategy = new DaoAuthenticationProvider();
        daoAuthenticationStrategy.setPasswordEncoder(this.passwordEncoder());
        daoAuthenticationStrategy.setUserDetailsService(this.userDetailsService());

        return daoAuthenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> this.userDetailsService.findByUsername(username).get();
    }
}
package com.spacecodee.springbootsecurityopentemplate.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class SecurityCacheConfig {

    @Value("${security.cache.max-size}")
    private int maxSize;

    @Value("${security.cache.expire-after-minutes}")
    private int expireAfterMinutes;

    private <T> @NotNull Cache<String, T> buildCache(int expiresInMinutes) {
        return CacheBuilder.newBuilder()
                .maximumSize(this.maxSize)
                .expireAfterWrite(expiresInMinutes, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    @Bean("tokenCache")
    Cache<String, JwtTokenEntity> tokenCache() {
        return buildCache(this.expireAfterMinutes);
    }

    @Bean("tokenStateCache")
    Cache<String, TokenStateEnum> tokenStateCache() {
        return buildCache(this.expireAfterMinutes);
    }

    @Bean("userDetailsCache")
    Cache<String, UserSecurityDTO> userDetailsCache() {
        return buildCache(this.expireAfterMinutes);
    }

    @Bean("publicPathsCache")
    Cache<String, List<String>> publicPathsCache() {
        return buildCache(this.expireAfterMinutes);
    }

    @Bean("userOperationsCache")
    Cache<String, List<OperationSecurityDTO>> userOperationsCache() {
        return buildCache(this.expireAfterMinutes);
    }

    @Bean("localeCache")
    Cache<String, Locale> localeCache() {
        return buildCache(this.expireAfterMinutes);
    }

}

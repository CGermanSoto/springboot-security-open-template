package com.spacecodee.springbootsecurityopentemplate.security.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class RateLimitConfig {

    @Bean
    LoadingCache<String, Integer> requestCountsCache(
            @Value("${security.rate-limit.duration-minutes}") int durationMinutes) {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(durationMinutes, TimeUnit.MINUTES)
                .recordStats()
                .maximumSize(10000)
                .build(new CacheLoader<>() {
                    @Override
                    @NotNull
                    public Integer load(@NotNull String key) {
                        return 0;
                    }
                });
    }

    @Bean
    CacheStats cacheStats(LoadingCache<String, Integer> cache) {
        return cache.stats();
    }

}

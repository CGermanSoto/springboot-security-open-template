package com.spacecodee.springbootsecurityopentemplate.cache.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityPathDTO;
import com.spacecodee.springbootsecurityopentemplate.service.security.operation.IOperationSecurityService;
import com.spacecodee.springbootsecurityopentemplate.cache.ISecurityCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityCacheServiceImpl implements ISecurityCacheService {

    private final Cache<String, List<String>> publicPathsCache;
    private final Cache<String, List<OperationSecurityDTO>> userOperationsCache;
    private final Cache<String, Locale> localeCache;
    private final IOperationSecurityService operationSecurityService;

    public List<String> getPublicPaths() {
        try {
            return this.publicPathsCache.get("publicPaths", () -> this.operationSecurityService.findByPublicAccess()
                    .stream()
                    .map(OperationSecurityPathDTO::getFullPath)
                    .toList());
        } catch (ExecutionException e) {
            log.error("Error loading public paths from cache: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<OperationSecurityDTO> getUserOperations(String username,
                                                        @NotNull Supplier<List<OperationSecurityDTO>> loader) {
        try {
            return this.userOperationsCache.get(username, loader::get);
        } catch (ExecutionException e) {
            log.error("Error loading user operations from cache for user {}: {}", username, e.getMessage());
            return Collections.emptyList();
        }
    }

    public Locale getCachedLocale(String key, @NotNull Supplier<Locale> loader) {
        try {
            return this.localeCache.get(key, loader::get);
        } catch (ExecutionException e) {
            log.error("Error loading locale from cache for key {}: {}", key, e.getMessage());
            return Locale.getDefault();
        }
    }

    @Scheduled(fixedRateString = "${security.cache.refresh-rate-minutes}", timeUnit = TimeUnit.MINUTES)
    public void refreshCaches() {
        this.publicPathsCache.invalidateAll();
        this.userOperationsCache.invalidateAll();
        this.localeCache.invalidateAll();
        log.info("Security caches refreshed at: {}", LocalDateTime.now());
    }

    public CacheStats getPublicPathsCacheStats() {
        return this.publicPathsCache.stats();
    }

    public CacheStats getUserOperationsCacheStats() {
        return this.userOperationsCache.stats();
    }

    public CacheStats getLocaleCacheStats() {
        return this.localeCache.stats();
    }

    @Override
    public Map<String, CacheStats> getAllCacheStats() {
        return Map.of(
                "publicPaths", this.publicPathsCache.stats(),
                "userOperations", this.userOperationsCache.stats(),
                "locale", this.localeCache.stats());
    }

}

package com.spacecodee.springbootsecurityopentemplate.service.security.token.cache;

import com.google.common.cache.CacheStats;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public interface ISecurityCacheService {

    List<String> getPublicPaths();

    List<OperationSecurityDTO> getUserOperations(String username, Supplier<List<OperationSecurityDTO>> loader);

    Locale getCachedLocale(String key, Supplier<Locale> loader);

    void refreshCaches();

    CacheStats getPublicPathsCacheStats();

    CacheStats getUserOperationsCacheStats();

    CacheStats getLocaleCacheStats();

    Map<String, CacheStats> getAllCacheStats();

}

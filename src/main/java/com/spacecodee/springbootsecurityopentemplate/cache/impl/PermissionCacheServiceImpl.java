package com.spacecodee.springbootsecurityopentemplate.cache.impl;

import com.google.common.cache.Cache;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cache.IPermissionCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionCacheServiceImpl implements IPermissionCacheService {

    private final Cache<String, List<String>> permissionCache;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    public void cachePermissions(String roleId, List<String> permissions) {
        try {
            this.permissionCache.put(roleId, permissions);
            log.debug("Permissions cached for role: {}", roleId);
        } catch (Exception e) {
            log.error("Error caching permissions: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("permission.cache.error");
        }
    }

    @Override
    public List<String> getPermissionsFromCache(String roleId) {
        return this.permissionCache.getIfPresent(roleId);
    }

    @Override
    public void removeFromCache(String roleId) {
        try {
            this.permissionCache.invalidate(roleId);
            log.debug("Permissions removed from cache for role: {}", roleId);
        } catch (Exception e) {
            log.error("Error removing permissions from cache: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("permission.cache.remove.error");
        }
    }

    @Override
    public void clearCache() {
        try {
            this.permissionCache.invalidateAll();
            log.debug("Permission cache cleared successfully");
        } catch (Exception e) {
            log.error("Error clearing permission cache: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("permission.cache.clear.error");
        }
    }

}
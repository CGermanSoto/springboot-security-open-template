package com.spacecodee.springbootsecurityopentemplate.cache.impl;

import com.google.common.cache.Cache;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cache.ITokenCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenCacheServiceImpl implements ITokenCacheService {

    private final Cache<String, JwtTokenEntity> tokenCache;
    private final Cache<String, TokenStateEnum> tokenStateCache;
    private final Cache<String, UserSecurityDTO> userDetailsCache;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    public void cacheToken(String token, JwtTokenEntity tokenEntity) {
        try {
            this.tokenCache.put(token, tokenEntity);
            log.debug("Token cached successfully");
        } catch (Exception e) {
            log.error("Error caching token: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cache.error");
        }
    }

    @Override
    public JwtTokenEntity getFromCache(String token) {
        try {
            return this.tokenCache.getIfPresent(token);
        } catch (Exception e) {
            log.error("Error retrieving token from cache: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cache.retrieve.error");
        }
    }

    @Override
    public void removeFromCache(String token) {
        try {
            this.tokenCache.invalidate(token);
            log.debug("Token removed from cache");
        } catch (Exception e) {
            log.error("Error removing token from cache: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cache.remove.error");
        }
    }

    @Override
    public void clearCache() {
        try {
            this.tokenCache.invalidateAll();
            log.debug("Cache cleared successfully");
        } catch (Exception e) {
            log.error("Error clearing cache: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cache.clear.error");
        }
    }

    @Override
    public void cacheTokenState(String token, TokenStateEnum state) {
        try {
            this.tokenStateCache.put(token, state);
            log.debug("Token state cached");
        } catch (Exception e) {
            log.error("Error caching token state: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cache.state.error");
        }
    }

    @Override
    public TokenStateEnum getTokenStateFromCache(String token) {
        return this.tokenStateCache.getIfPresent(token);
    }

    @Override
    public void cacheUserDetails(String username, UserSecurityDTO userDetails) {
        try {
            this.userDetailsCache.put(username, userDetails);
            log.debug("User details cached for: {}", username);
        } catch (Exception e) {
            log.error("Error caching user details: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cache.user.error");
        }
    }

    @Override
    public UserSecurityDTO getUserDetailsFromCache(String username) {
        return this.userDetailsCache.getIfPresent(username);
    }

}

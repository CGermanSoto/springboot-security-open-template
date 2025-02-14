package com.spacecodee.springbootsecurityopentemplate.service.security.token.usage;

import java.util.Map;

public interface ITokenUsageTrackingService {

    void trackTokenExpiration(String token);

    void trackTokenAccess(String token, String operation);

    void trackTokenRefresh(String oldToken, String newToken, String username);

    Map<String, Integer> getTokenUsageStatistics(String token);

}

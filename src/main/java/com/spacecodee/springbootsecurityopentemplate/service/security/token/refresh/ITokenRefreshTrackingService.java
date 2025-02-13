package com.spacecodee.springbootsecurityopentemplate.service.security.token.refresh;

public interface ITokenRefreshTrackingService {

    void trackRefresh(String oldToken, String newToken, String username);

    int getRefreshCount(String token);

    boolean hasExceededRefreshLimit(String token);

}

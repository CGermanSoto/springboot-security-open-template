package com.spacecodee.springbootsecurityopentemplate.service.security.token.ratelimit;

public interface ITokenRateLimitService {

    void checkRateLimit(String key);

    void recordAttempt(String key);

    void clearAttempts(String key);

}

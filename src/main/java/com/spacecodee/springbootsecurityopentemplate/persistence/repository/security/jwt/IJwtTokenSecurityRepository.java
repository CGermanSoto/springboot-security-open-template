package com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Repository
public interface IJwtTokenSecurityRepository extends JpaRepository<JwtTokenEntity, Integer> {

    Optional<JwtTokenEntity> findByUserEntity_Username(String username);

    Optional<JwtTokenEntity> findByToken(String token);

    boolean existsByToken(String jwt);

    void deleteByToken(String token);

    void deleteByUserEntityId(Integer userId);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.isRevoked = true, t.revokedAt = :revokedAt WHERE t.token = :token")
    void revokeToken(@Param("token") String token, @Param("revokedAt") Instant revokedAt);

    @Query("SELECT t.isRevoked FROM JwtTokenEntity t WHERE t.token = :token")
    boolean isTokenRevoked(@Param("token") String token);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.isRevoked = true, t.revokedAt = :revokedAt WHERE t.userEntity.username = :username")
    void revokeAllUserTokens(@Param("username") String username, @Param("revokedAt") Instant revokedAt);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.token = :newToken, t.updatedAt = :updateTime WHERE t.token = :oldToken")
    void updateToken(@Param("oldToken") String oldToken, @Param("newToken") String newToken,
                     @Param("updateTime") Instant updateTime);

    @Query("SELECT t.createdAt FROM JwtTokenEntity t WHERE t.token = :token")
    Instant findTokenCreationTime(@Param("token") String token);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.refreshCount = t.refreshCount + 1, t.lastRefreshAt = CURRENT_TIMESTAMP, "
            +
            "t.previousToken = t.token, t.token = :newToken WHERE t.token = :oldToken AND t.userEntity.username = :username")
    void trackTokenRefresh(@Param("oldToken") String oldToken, @Param("newToken") String newToken,
                           @Param("username") String username);

    @Query("SELECT t.refreshCount FROM JwtTokenEntity t WHERE t.token = :token")
    int getTokenRefreshCount(@Param("token") String token);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.isRevoked = true WHERE t.token IN :tokens")
    void invalidateTokensBatch(@Param("tokens") List<String> tokens);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.isRevoked = true WHERE t.expiryDate < :now")
    void invalidateExpiredTokens(@Param("now") Instant now);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.isRevoked = true WHERE t.userEntity.username = :username")
    void invalidateUserTokens(@Param("username") String username);

    @Query("SELECT t.token FROM JwtTokenEntity t WHERE t.expiryDate < :now AND t.isRevoked = false")
    List<String> findInactiveTokens(@Param("now") Instant now);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.usageCount = t.usageCount + 1, " +
            "t.lastAccessAt = :accessTime, " +
            "t.lastOperation = :operation WHERE t.token = :token")
    void incrementTokenUsage(@Param("token") String token,
                             @Param("operation") String operation,
                             @Param("accessTime") Instant accessTime);

    @Query("SELECT t.usageCount FROM JwtTokenEntity t WHERE t.token = :token")
    int getTokenUsageCount(@Param("token") String token);

    @Modifying
    @Query("UPDATE JwtTokenEntity t SET t.lastAccessAt = :accessTime WHERE t.token = :token")
    void updateLastAccess(@Param("token") String token, @Param("accessTime") Instant accessTime);

    @Query("SELECT new map(t.lastOperation as operation, COUNT(t) as count) " +
            "FROM JwtTokenEntity t WHERE t.token = :token GROUP BY t.lastOperation")
    Map<String, Integer> getTokenUsageStats(@Param("token") String token);

    @Modifying
    @Query("DELETE FROM JwtTokenEntity t WHERE t.expiryDate < :now")
    int deleteExpiredTokens(@Param("now") Instant now);

    @Modifying
    @Query("DELETE FROM JwtTokenEntity t WHERE t.isRevoked = true")
    int deleteRevokedTokens();

    @Modifying
    @Query("DELETE FROM JwtTokenEntity t WHERE t.lastAccessAt < :threshold")
    int deleteInactiveTokens(@Param("threshold") Instant threshold);

    @Modifying
    @Query("DELETE FROM JwtTokenEntity t WHERE t.state = 'BLACKLISTED'")
    int deleteBlacklistedTokens();

    Optional<JwtTokenEntity> findByPreviousToken(String previousToken);

    Optional<JwtTokenEntity> findFirstByUserEntity_UsernameOrderByCreatedAtDesc(String username);

    @Query("SELECT t FROM JwtTokenEntity t WHERE t.userEntity.username = :username " +
            "AND t.isRevoked = false AND t.expiryDate > CURRENT_TIMESTAMP " +
            "ORDER BY t.createdAt DESC")
    Optional<JwtTokenEntity> findActiveTokenByUsername(@Param("username") String username);

    Optional<JwtTokenEntity> findJwtTokenEntityByUserEntity_Username(String username);
}
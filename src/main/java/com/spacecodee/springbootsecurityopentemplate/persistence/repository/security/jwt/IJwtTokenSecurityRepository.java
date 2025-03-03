package com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface IJwtTokenSecurityRepository extends JpaRepository<JwtTokenEntity, Integer> {

    Optional<JwtTokenEntity> findByToken(String token);

    @Modifying
    @Query("DELETE FROM JwtTokenEntity t WHERE t.token = :token")
    void deleteByToken(@Param("token") String token);

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

    @Query("SELECT t FROM JwtTokenEntity t WHERE t.expiryDate < :now AND t.isRevoked = false")
    List<JwtTokenEntity> findExpiredTokens(@Param("now") Instant now);

    List<JwtTokenEntity> findAllByUserEntity_Username(String username);

    Optional<JwtTokenEntity> findFirstByUserEntity_UsernameOrderByCreatedAtDesc(String username);
}
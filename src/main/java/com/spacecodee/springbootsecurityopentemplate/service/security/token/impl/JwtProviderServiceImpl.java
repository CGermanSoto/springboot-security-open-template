package com.spacecodee.springbootsecurityopentemplate.service.security.token.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenInvalidException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.builder.ITokenBuilderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.key.IJwtKeyService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl.TokenValidationChainImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProviderServiceImpl implements IJwtProviderService {

    private static final int EXPECTED_JWT_PARTS = 3;
    private static final int JWT_PAYLOAD_INDEX = 1;
    private static final String AUTH_TOKEN_INVALID = "auth.token.invalid";

    private final ExceptionShortComponent exceptionShortComponent;
    private final TokenValidationChainImpl tokenValidationChain;
    private final ITokenBuilderService tokenBuilder;
    private final IJwtKeyService jwtKeyService;

    @Override
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        if (userDetails == null) {
            log.error("Attempt to generate token for null user details");
            throw this.exceptionShortComponent.invalidParameterException(JwtProviderServiceImpl.AUTH_TOKEN_INVALID,
                    "null");
        }

        return this.buildToken(new TokenClaims(userDetails.getUsername(), extraClaims));
    }

    @Override
    public Map<String, Object> generateExtraClaims(UserSecurityDTO userDetails) {
        if (userDetails == null || userDetails.getRoleSecurityDTO() == null) {
            log.error("User details or role is null");
            throw this.exceptionShortComponent.invalidParameterException(AUTH_TOKEN_INVALID, "null");
        }

        // Simplified claims with only essential data
        return Map.of(
                "userId", userDetails.getId(),
                "role", userDetails.getRoleSecurityDTO().getName(), // Only role name
                "type", "Bearer");
    }

    @Override
    public String buildToken(@NotNull TokenClaims tokenClaims) {
        try {
            return this.tokenBuilder.build(tokenClaims, this.jwtKeyService.generateKey());
        } catch (Exception e) {
            log.error("Error building token for subject: {}", tokenClaims.subject(), e);
            throw this.exceptionShortComponent.tokenUnexpectedException("auth.token.error");
        }
    }

    @Override
    public String extractUsername(String jwt) {
        try {
            return this.extractClaims(jwt).getSubject();
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.signature.invalid");
        } catch (ExpiredJwtException e) {
            log.error("JWT token expired: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenExpiredException("auth.token.expired");
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.malformed");
        } catch (Exception e) {
            log.error("Error extracting username from token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException(JwtProviderServiceImpl.AUTH_TOKEN_INVALID);
        }
    }

    @Override
    public Integer extractUserId(String jwt) {
        try {
            Claims claims = this.extractClaims(jwt);
            return claims.get("userId", Integer.class);
        } catch (Exception e) {
            log.error("Error extracting user ID from token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.userid.invalid");
        }
    }

    @Override
    public String extractJwtFromRequest(HttpServletRequest request) {
        if (request == null) {
            log.error("Null HTTP request received");
            throw this.exceptionShortComponent.invalidParameterException("auth.request.null");
        }

        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken)) {
            log.debug("No authorization header found");
            throw this.exceptionShortComponent.tokenNotFoundException("auth.token.header.missing");
        }

        if (!bearerToken.startsWith("Bearer ")) {
            log.debug("Invalid authorization header format");
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.header.invalid");
        }

        return bearerToken.substring(7);
    }

    @Override
    public Instant extractExpiration(String jwt) {
        try {
            return this.extractClaims(jwt).getExpiration().toInstant();
        } catch (Exception e) {
            log.error("Error extracting token expiration", e);
            throw this.exceptionShortComponent.tokenInvalidException(JwtProviderServiceImpl.AUTH_TOKEN_INVALID);
        }
    }

    @Override
    public Claims extractClaims(String jwt) {
        try {
            this.tokenValidationChain.validateToken(jwt);
            return Jwts.parser()
                    .verifyWith(this.jwtKeyService.generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.debug("JWT expired when extracting claims: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenExpiredException("auth.token.expired", e.getMessage());
        } catch (JwtTokenInvalidException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error extracting claims from token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException(AUTH_TOKEN_INVALID);
        }
    }

    @Override
    public Claims extractClaimsWithoutValidation(String jwt) {
        log.debug("Attempting to extract claims without validation for JWT");

        if (!StringUtils.hasText(jwt)) {
            log.error("JWT token is null or empty");
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.empty");
        }

        try {
            String[] jwtParts = jwt.split("\\.");
            this.validateJwtFormat(jwtParts);

            String payload = this.decodeJwtPayload(jwtParts[JWT_PAYLOAD_INDEX]);
            return this.parseJwtClaims(payload);

        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT format: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.malformed");
        } catch (Exception e) {
            log.error("Failed to parse JWT claims: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenExpiredException("auth.token.parse.error");
        }
    }

    @Override
    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = extractClaims(jwt);
            return !claims.getExpiration().before(new Date());
        } catch (JwtTokenInvalidException | JwtTokenExpiredException e) {
            return false;
        } catch (Exception e) {
            log.error("Error validating token", e);
            return false;
        }
    }

    private void validateJwtFormat(String @NotNull [] parts) {
        if (parts.length != EXPECTED_JWT_PARTS) {
            log.error("Invalid JWT format - expected {} parts but got {}", EXPECTED_JWT_PARTS, parts.length);
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.invalid.format");
        }
    }

    private @NotNull String decodeJwtPayload(String encodedPayload) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedPayload);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private Claims parseJwtClaims(String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> claimsMap = mapper.readValue(payload, new TypeReference<>() {
            });
            return Jwts.claims().add(claimsMap).build();
        } catch (Exception e) {
            log.error("Failed parsing JWT claims: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.parse.error");
        }
    }
}
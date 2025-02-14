package com.spacecodee.springbootsecurityopentemplate.data.vo.jwt;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CreateJwtTokenVO {

    @NotBlank(message = "{validation.jwt.token.required}")
    private String token;

    @NotNull(message = "{validation.jwt.valid.required}")
    private Boolean valid;

    @NotNull(message = "{validation.jwt.expiry.required}")
    @Future(message = "{validation.jwt.expiry.future}")
    private Instant expiryDate;

    @NotNull(message = "{validation.jwt.user.required}")
    @Min(value = 1, message = "{validation.jwt.user.id.min}")
    private Integer userId;

    @NotNull(message = "{validation.jwt.state.required}")
    private String state;

    private Boolean isRevoked = false;
    private Instant revokedAt;

    @Size(max = 255, message = "{validation.jwt.revoked.reason.max}")
    private String revokedReason;

    @Min(value = 0, message = "{validation.jwt.refresh.count.min}")
    private Integer refreshCount = 0;
    private Instant lastRefreshAt;
    private String previousToken;

    @NotNull(message = "{validation.jwt.usage.count.min}")
    @Min(value = 0, message = "{validation.jwt.usage.count.min}")
    private Integer usageCount = 0;

    private Instant lastAccessAt;

    @Size(max = 100, message = "{validation.jwt.last.operation.max}")
    private String lastOperation;

    @NotBlank(message = "{validation.jwt.jti.required}")
    private String jti;
}

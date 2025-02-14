package com.spacecodee.springbootsecurityopentemplate.persistence.entity;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Entity()
@Table(name = "jwt_token", schema = "public")
public class JwtTokenEntity extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5856445426538329660L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jwt_token_id_gen")
    @SequenceGenerator(name = "jwt_token_id_gen", sequenceName = "jwt_token_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid = false;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @Column(name = "is_revoked", nullable = false)
    private Boolean isRevoked = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private TokenStateEnum state;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "revoked_reason")
    private String revokedReason;

    @Column(name = "refresh_count")
    private Integer refreshCount = 0;

    @Column(name = "last_refresh_at")
    private Instant lastRefreshAt;

    @Column(name = "previous_token")
    private String previousToken;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "last_access_at")
    private Instant lastAccessAt;

    @Column(name = "last_operation")
    private String lastOperation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity userEntity;

    @Column(name = "jti", nullable = false)
    private String jti;

}